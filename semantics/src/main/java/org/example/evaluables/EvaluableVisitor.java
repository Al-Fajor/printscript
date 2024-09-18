package org.example.evaluables;

import static org.example.Resolution.getFirstFailedResolution;
import static org.example.conditiontrees.AssignmentStatementTree.checkIdentifierExists;
import static org.example.conditiontrees.BinaryExpressionTree.anyTypeEmpty;
import static org.example.conditiontrees.BinaryExpressionTree.validateOperationTypes;
import static org.example.conditiontrees.DeclarationStatementTree.checkIdentifierDoesNotExist;
import static org.example.conditiontrees.FunctionCallStatementTree.getAllParameterTypes;
import static org.example.conditiontrees.FunctionCallStatementTree.getInvalidResolutionIfAny;
import static org.example.conditiontrees.FunctionCallStatementTree.resolveEachParameter;
import static org.example.conditiontrees.IdentifierTree.existingIdentifier;
import static org.example.conditiontrees.IdentifierTree.identifierNotFound;
import static org.example.conditiontrees.IfStatementTree.checkIsBooleanIdentifier;
import static org.example.conditiontrees.IfStatementTree.resolveInnerStatements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.example.Environment;
import org.example.ResolvedType;
import org.example.SemanticSuccess;
import org.example.ast.BinaryExpression;
import org.example.ast.Identifier;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.statement.*;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.conditiontrees.DeclarationAssignmentStatementTree;
import org.example.conditiontrees.FunctionCallStatementTree;
import org.example.conditiontrees.LiteralTree;
import org.example.languageconfig.Language;

public class EvaluableVisitor implements AstComponentVisitor<EvaluableResolution> {
	public static final SemanticSuccess SUCCESS = new SemanticSuccess();
	private final Environment env;
	private final Language lang = new Language();

	public EvaluableVisitor(Environment env) {
		this.env = env;
	}

	public EvaluableVisitor withEnv(Environment env) {
		return new EvaluableVisitor(env);
	}

	@Override
	public EvaluableResolution visit(BinaryExpression expression) {
		var leftResolution = expression.getLeftComponent().accept(this);
		var rightResolution = expression.getRightComponent().accept(this);

		if (anyTypeEmpty(leftResolution, rightResolution)) {
			throw new IllegalStateException(
					"Invalid AST structure: received BinaryExpression with an empty declarationType");
		}

		@SuppressWarnings({"OptionalGetWithoutIsPresent"}) // Safe because of anyTypeEmpty
		ResolvedType leftType = leftResolution.evaluatedType().get();
		@SuppressWarnings({"OptionalGetWithoutIsPresent"}) // Safe because of anyTypeEmpty
		ResolvedType rightType = rightResolution.evaluatedType().get();

		return getFirstFailedResolution(leftResolution, rightResolution)
				.orElse(validateOperationTypes(expression, leftType, rightType, lang));
	}

	@Override
	public EvaluableResolution visit(IfStatement ifStatement) {
		EvaluableVisitor visitorCopy = this.withEnv(env.copy());

		EvaluableResolution[] trueClauseStatementResolutions =
				resolveInnerStatements(visitorCopy, ifStatement.trueClause())
						.toArray(EvaluableResolution[]::new);

		return getFirstFailedResolution(trueClauseStatementResolutions)
				.orElse(checkIsBooleanIdentifier(ifStatement.conditionalIdentifier(), env));
	}

	@Override
	public EvaluableResolution visit(IfElseStatement ifElseStatement) {
		// Copy visitor to avoid local scopes from merging with the global one
		EvaluableVisitor trueClauseVisitor = this.withEnv(env.copy());
		EvaluableVisitor falseClauseVisitor = this.withEnv(env.copy());

		Stream<EvaluableResolution> trueClauseResolutions =
				resolveInnerStatements(trueClauseVisitor, ifElseStatement.trueClause());

		Stream<EvaluableResolution> falseClauseResolutions =
				resolveInnerStatements(falseClauseVisitor, ifElseStatement.falseClause());

		EvaluableResolution[] allResolutions =
				Stream.concat(trueClauseResolutions, falseClauseResolutions)
						.toArray(EvaluableResolution[]::new);

		return getFirstFailedResolution(allResolutions)
				.orElse(checkIsBooleanIdentifier(ifElseStatement.conditionalIdentifier(), env));
	}

	@Override
	public EvaluableResolution visit(Literal<?> literal) {
		return new EvaluableResolution(
				SUCCESS,
				Optional.of(LiteralTree.mapToDeclarationType(literal)),
				Optional.empty(),
				Optional.empty());
	}

	@Override
	public EvaluableResolution visit(AssignmentStatement statement) {
		Identifier identifier = statement.getIdentifier();
		EvaluableResolution assignedValueResolution =
				statement.getEvaluableComponent().accept(this);

		return getFirstFailedResolution(assignedValueResolution)
				.orElse(checkIdentifierExists(statement, identifier, assignedValueResolution, env));
	}

	@Override
	public EvaluableResolution visit(DeclarationAssignmentStatement statement) {
		EvaluableResolution assignedValueResolution =
				statement.getEvaluableComponent().accept(this);

		return getFirstFailedResolution(assignedValueResolution)
				.orElseGet(
						() ->
								DeclarationAssignmentStatementTree.checkIdentifierDoesNotExist(
										statement, assignedValueResolution, env));
	}

	@Override
	public EvaluableResolution visit(DeclarationStatement statement) {
		return checkIdentifierDoesNotExist(statement, env);
	}

	@Override
	public EvaluableResolution visit(FunctionCallStatement statement) {
		List<EvaluableResolution> resolvedParameters =
				resolveEachParameter(statement.getParameters(), this);

		Optional<EvaluableResolution> firstInvalidParameterResolution =
				getInvalidResolutionIfAny(resolvedParameters);
		if (firstInvalidParameterResolution.isPresent())
			return firstInvalidParameterResolution.get();

		List<ResolvedType> types = getAllParameterTypes(resolvedParameters);
		String functionName = statement.getIdentifier().getName();
		return FunctionCallStatementTree.checkFunctionIsDeclared(
				env, statement, types, functionName);
	}

	@Override
	public EvaluableResolution visit(Identifier identifier) {
		if (env.isVariableDeclared(identifier.getName())) {
			return existingIdentifier(env, identifier);
		} else {
			return identifierNotFound(identifier);
		}
	}

	@Override
	public EvaluableResolution visit(Parameters parameters) {
		return null;
	}
}
