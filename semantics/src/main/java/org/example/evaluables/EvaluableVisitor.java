package org.example.evaluables;

import static org.example.Resolution.getFirstFailedResolution;

import java.util.List;
import java.util.Optional;
import org.example.Environment;
import org.example.Resolution;
import org.example.SemanticFailure;
import org.example.SemanticSuccess;
import org.example.ast.BinaryExpression;
import org.example.ast.BinaryOperator;
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
				.orElse(validateOperationTypes(expression, leftType, rightType));
	}

	private static boolean anyTypeEmpty(
			EvaluableResolution leftResolution, EvaluableResolution rightResolution) {
		return leftResolution.evaluatedType().isEmpty()
				|| rightResolution.evaluatedType().isEmpty();
	}

	private EvaluableResolution validateOperationTypes(
			BinaryExpression expression, DeclarationType leftType, DeclarationType rightType) {

		if (lang.isOperationSupported(leftType, expression.getOperator(), rightType)) {
			return successfulOperationResolution(expression, leftType, rightType);
		} else {
			return failedOperationResolution(expression, rightType, leftType);
		}
	}

	private EvaluableResolution successfulOperationResolution(
			BinaryExpression expression, DeclarationType leftType, DeclarationType rightType) {
		return new EvaluableResolution(
				SUCCESS,
				Optional.of(lang.getResolvedType(leftType, expression.getOperator(), rightType)),
				Optional.empty());
	}

	private EvaluableResolution failedOperationResolution(
			BinaryExpression expression, DeclarationType rightType, DeclarationType leftType) {
		return EvaluableResolution.failure(
				"Cannot perform operation because types are incompatible: "
						+ rightType
						+ " "
						+ getSymbol(expression.getOperator())
						+ " "
						+ leftType
						+ " ",
				expression.getStart(),
				expression.getEnd());
	}

	private String getSymbol(BinaryOperator operator) {
		// TODO: could move to BinaryOperator
		return switch (operator) {
			case SUM -> "+";
			case SUBTRACTION -> "-";
			case MULTIPLICATION -> "*";
			case DIVISION -> "/";
			default ->
					throw new RuntimeException(
							"Invalid operator: "
									+ operator
									+ "(EvaluableResolution return for this type yet to be implemented)");
		};
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
				SUCCESS, Optional.ofNullable(mapToDeclarationType(literal)), Optional.empty());
	}

	private DeclarationType mapToDeclarationType(Literal<?> literal) {
		return switch (literal.getValue()) {
			case String ignoredString -> DeclarationType.STRING;
			case Number ignoredNumber -> DeclarationType.NUMBER;
			case null -> null;
			default -> throw new IllegalStateException("Unexpected value: " + literal.getValue());
		};
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
								statement, identifierResolution, assignedValueResolution));
	}

	private EvaluableResolution validateDeclarationOrAssignation(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution) {
		boolean isDeclaration = identifierResolution.type().isPresent();
		if (isDeclaration) {
			return checkIdentifierDoesNotExist(
					statement, identifierResolution, assignedValueResolution);
		} else {
			return checkIdentifierExists(statement, identifierResolution, assignedValueResolution);
		}
	}

	private EvaluableResolution checkIdentifierExists(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution) {
		if (env.isVariableDeclared(identifierResolution.name())) {
			return checkAssigningToValidValue(
					statement, identifierResolution, assignedValueResolution);
		} else {
			return EvaluableResolution.failure(
					"Cannot assign non-existing identifier",
					statement.getStart(),
					statement.getEnd());
		}
	}

	private EvaluableResolution checkAssigningToValidValue(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution) {

		DeclarationType identifierType = env.getDeclarationType(identifierResolution.name());
		DeclarationType assignedValueType =
				assignedValueResolution
						.evaluatedType()
						.orElseThrow(
								() ->
										new IllegalStateException(
												"Received invalid AST: Assigning to value with no type"));

		if (identifierType == assignedValueType) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot assign type "
							+ assignedValueResolution.evaluatedType().get()
							+ " to "
							+ env.getDeclarationType(identifierResolution.name()),
					statement.getStart(),
					statement.getEnd());
		}
	}

	private EvaluableResolution checkIdentifierDoesNotExist(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution) {
		if (env.isVariableDeclared(identifierResolution.name())) {
			return EvaluableResolution.failure(
					"Variable has already been declared", statement.getStart(), statement.getEnd());
		} else {
			return checkIsSimpleDeclaration(
					statement, identifierResolution, assignedValueResolution);
		}
	}

	private EvaluableResolution checkIsSimpleDeclaration(
			AssignationStatement statement,
			IdentifierResolution identifierResolution,
			EvaluableResolution assignedValueResolution) {
		boolean isSimpleDeclaration = assignedValueResolution.evaluatedType().isEmpty();
		if (isSimpleDeclaration) {
			return new EvaluableResolution(
					SUCCESS, identifierResolution.type(), Optional.of(identifierResolution.name()));
		} else {

			@SuppressWarnings(
					"OptionalGetWithoutIsPresent") // Safe because declarations always have a type
			DeclarationType leftType = identifierResolution.type().get();
			DeclarationType rightType = assignedValueResolution.evaluatedType().get();
			String name = identifierResolution.name();

			return checkDeclaringWithValidValue(statement, leftType, rightType, name);
		}
	}

	private EvaluableResolution checkDeclaringWithValidValue(
			AssignationStatement statement,
			DeclarationType identifierType,
			DeclarationType assignedType,
			String name) {

		if (identifierType == assignedType) {
			return new EvaluableResolution(SUCCESS, Optional.of(identifierType), Optional.of(name));
		} else {
			return EvaluableResolution.failure(
					"Cannot assign value of type "
							+ assignedType
							+ " to variable of type"
							+ identifierType,
					statement.getStart(),
					statement.getEnd());
		}
	}

	@Override
	public EvaluableResolution visit(Declaration statement) {
		return null;
	}

	@Override
	public EvaluableResolution visit(FunctionCallStatement statement) {
		IdentifierResolution functionCallResolution = statement.getLeft().accept(identifierVisitor);
		Parameters parameters = statement.getRight();

		List<EvaluableResolution> resolvedParameters =
				parameters.getParameters().stream()
						.map(astComponent -> astComponent.accept(this))
						.toList();

		Optional<EvaluableResolution> firstInvalidParameterResolution =
				resolvedParameters.stream().filter(Resolution::failed).findFirst();

		if (firstInvalidParameterResolution.isPresent())
			return firstInvalidParameterResolution.get();

		@SuppressWarnings(
				"OptionalGetWithoutIsPresent") // Safe because of firstInvalidParameterResolution
		List<DeclarationType> types =
				resolvedParameters.stream()
						.map(resolution -> resolution.evaluatedType().get())
						.toList();

		String functionName = functionCallResolution.name();

		return getFirstFailedResolution(functionCallResolution)
				.map(EvaluableResolution::castFrom)
				.orElse(isFunctionDeclared(statement, types, functionName));
	}

	private EvaluableResolution isFunctionDeclared(
			FunctionCallStatement statement, List<DeclarationType> types, String functionName) {
		if (env.isFunctionDeclared(functionName, types)) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot resolve function signature " + functionName + "(" + types + ").",
					statement.getStart(),
					statement.getEnd());
		}
	}

	@Override
	public EvaluableResolution visit(StatementBlock statementBlock) {
		return null;
	}

	@Override
	public EvaluableResolution visit(Identifier identifier) {
		if (env.isVariableDeclared(identifier.getName())) {
			return new EvaluableResolution(
					SUCCESS,
					Optional.of(env.getDeclarationType(identifier.getName())),
					Optional.of(identifier.getName()));
		} else {
			return new EvaluableResolution(
					new SemanticFailure(
							"Cannot find identifier " + identifier.getName(),
							Optional.of(identifier.getStart()),
							Optional.of(identifier.getEnd())),
					Optional.empty(),
					Optional.of(identifier.getName()));
		}
	}
}
