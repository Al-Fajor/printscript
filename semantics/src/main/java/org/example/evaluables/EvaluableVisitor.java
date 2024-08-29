package org.example.evaluables;

import static org.example.Resolution.getFirstFailedResolution;
import static org.example.conditiontrees.AssignationStatementTree.validateDeclarationOrAssignation;
import static org.example.conditiontrees.BinaryExpressionTree.anyTypeEmpty;
import static org.example.conditiontrees.BinaryExpressionTree.validateOperationTypes;
import static org.example.conditiontrees.FunctionCallStatementTree.getAllParameterTypes;
import static org.example.conditiontrees.FunctionCallStatementTree.getInvalidResolutionIfAny;
import static org.example.conditiontrees.FunctionCallStatementTree.resolveEachParameter;
import static org.example.conditiontrees.IdentifierTree.existingIdentifier;
import static org.example.conditiontrees.IdentifierTree.identifierNotFound;

import java.util.List;
import java.util.Optional;
import org.example.Environment;
import org.example.SemanticSuccess;
import org.example.ast.BinaryExpression;
import org.example.ast.Conditional;
import org.example.ast.Declaration;
import org.example.ast.DeclarationType;
import org.example.ast.Identifier;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.conditiontrees.FunctionCallStatementTree;
import org.example.conditiontrees.LiteralTree;
import org.example.externalization.Language;
import org.example.identifiers.IdentifierResolution;
import org.example.identifiers.IdentifierVisitor;

public class EvaluableVisitor implements AstComponentVisitor<EvaluableResolution> {
	public static final SemanticSuccess SUCCESS = new SemanticSuccess();
	private final Environment env;
	private final IdentifierVisitor identifierVisitor;
	private final Language lang = new Language();

	public EvaluableVisitor(Environment env, IdentifierVisitor identifierVisitor) {
		this.env = env;
		this.identifierVisitor = identifierVisitor;
	}

	public EvaluableVisitor withEnv(Environment env) {
		return new EvaluableVisitor(env, identifierVisitor);
	}

	@Override
	public EvaluableResolution visit(BinaryExpression expression) {
		var leftResolution = expression.getLeftComponent().accept(this);
		var rightResolution = expression.getRightComponent().accept(this);

		if (anyTypeEmpty(leftResolution, rightResolution)) {
			throw new IllegalStateException(
					"PrintScript does not support null values: received BinaryExpression with an empty type");
		}

		@SuppressWarnings({"OptionalGetWithoutIsPresent"}) // Safe because of anyTypeEmpty
		DeclarationType leftType = leftResolution.evaluatedType().get();
		@SuppressWarnings({"OptionalGetWithoutIsPresent"}) // Safe because of anyTypeEmpty
		DeclarationType rightType = rightResolution.evaluatedType().get();

		return getFirstFailedResolution(leftResolution, rightResolution)
				.orElse(validateOperationTypes(expression, leftType, rightType, lang));
	}

	@Override
	public EvaluableResolution visit(Conditional conditional) {
		return null;
	}

	@Override
	public EvaluableResolution visit(IfStatement ifStatement) {
		return null;
	}

	@Override
	public EvaluableResolution visit(Literal<?> literal) {
		return new EvaluableResolution(
				SUCCESS,
				Optional.ofNullable(LiteralTree.mapToDeclarationType(literal)),
				Optional.empty());
	}

	@Override
	public EvaluableResolution visit(Parameters parameters) {
		return null;
	}

	@Override
	public EvaluableResolution visit(AssignationStatement statement) {
		IdentifierResolution identifierResolution = statement.getLeft().accept(identifierVisitor);
		EvaluableResolution assignedValueResolution = statement.getRight().accept(this);

		return getFirstFailedResolution(assignedValueResolution)
				.orElse(
						validateDeclarationOrAssignation(
								statement, identifierResolution, assignedValueResolution, env));
	}

	@Override
	public EvaluableResolution visit(Declaration statement) {
		return null;
	}

	@Override
	public EvaluableResolution visit(FunctionCallStatement statement) {
		IdentifierResolution functionCallResolution = statement.getLeft().accept(identifierVisitor);
		Parameters parameters = statement.getRight();

		List<EvaluableResolution> resolvedParameters = resolveEachParameter(parameters, this);

		Optional<EvaluableResolution> firstInvalidParameterResolution =
				getInvalidResolutionIfAny(resolvedParameters);

		if (firstInvalidParameterResolution.isPresent())
			return firstInvalidParameterResolution.get();

		List<DeclarationType> types = getAllParameterTypes(resolvedParameters);

		String functionName = functionCallResolution.name();

		return getFirstFailedResolution(functionCallResolution)
				.map(EvaluableResolution::castFrom)
				.orElse(
						FunctionCallStatementTree.isFunctionDeclared(
								env, statement, types, functionName));
	}

	@Override
	public EvaluableResolution visit(StatementBlock statementBlock) {
		return null;
	}

	@Override
	public EvaluableResolution visit(Identifier identifier) {
		if (env.isVariableDeclared(identifier.getName())) {
			return existingIdentifier(env, identifier);
		} else {
			return identifierNotFound(identifier);
		}
	}
}
