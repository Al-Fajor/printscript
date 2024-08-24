package org.example.evaluables;

import static org.example.Resolution.getFirstFailedResolution;

import java.util.List;
import java.util.Optional;
import org.example.Environment;
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
	private Environment env;
	private final IdentifierVisitor identifierVisitor;
	private final Language lang = new Language();

	public EvaluableVisitor(Environment env, IdentifierVisitor identifierVisitor) {
		this.env = env;
		this.identifierVisitor = identifierVisitor;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	@Override
	public EvaluableResolution visit(BinaryExpression expression) {
		var leftResolution = expression.getLeftComponent().accept(this);
		var rightResolution = expression.getRightComponent().accept(this);

		return getFirstFailedResolution(leftResolution, rightResolution)
				.orElse(validateOperationTypes(expression, leftResolution, rightResolution));
	}

	private EvaluableResolution validateOperationTypes(
			BinaryExpression expression,
			EvaluableResolution leftResolution,
			EvaluableResolution rightResolution) {
		if (lang.isOperationSupported(
				leftResolution.evaluatedType().get(),
				expression.getOperator(),
				rightResolution.evaluatedType().get())) {
			return successfulOperationResolution(expression, leftResolution, rightResolution);
		} else {
			return failedOperationResolution(expression, rightResolution, leftResolution);
		}
	}

	private EvaluableResolution successfulOperationResolution(
			BinaryExpression expression,
			EvaluableResolution leftResolution,
			EvaluableResolution rightResolution) {
		return new EvaluableResolution(
				new SemanticSuccess(),
				Optional.of(
						lang.getResolvedType(
								leftResolution.evaluatedType().get(),
								expression.getOperator(),
								rightResolution.evaluatedType().get())),
				Optional.empty());
	}

	private EvaluableResolution failedOperationResolution(
			BinaryExpression expression,
			EvaluableResolution rightResolution,
			EvaluableResolution leftResolution) {
		return EvaluableResolution.failure(
				"Cannot perform operation because types are incompatible: "
						+ rightResolution.evaluatedType().get()
						+ " "
						+ getSymbol(expression.getOperator())
						+ " "
						+ leftResolution.evaluatedType().get()
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
				new SemanticSuccess(),
				Optional.ofNullable(mapToDeclarationType(literal)),
				Optional.empty());
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
		IdentifierResolution leftResolution = statement.getLeft().accept(identifierVisitor);
		EvaluableResolution rightResolution = statement.getRight().accept(this);

		return getFirstFailedResolution(rightResolution)
				.orElse(
						validateDeclarationOrAssignation(
								statement, leftResolution, rightResolution));
	}

	private EvaluableResolution validateDeclarationOrAssignation(
			AssignationStatement statement,
			IdentifierResolution leftResolution,
			EvaluableResolution rightResolution) {
		boolean isDeclaration = leftResolution.type().isPresent();
		if (isDeclaration) {
			return checkIdentifierDoesNotExist(statement, leftResolution, rightResolution);
		} else {
			return checkIdentifierExists(statement, leftResolution, rightResolution);
		}
	}

	private EvaluableResolution checkIdentifierExists(
			AssignationStatement statement,
			IdentifierResolution leftResolution,
			EvaluableResolution rightResolution) {
		if (env.isVariableDeclared(leftResolution.name())) {
			return checkAssigningToValidValue(statement, leftResolution, rightResolution);
		} else {
			return EvaluableResolution.failure(
					"Cannot assign non-existing identifier",
					statement.getStart(),
					statement.getEnd());
		}
	}

	private EvaluableResolution checkAssigningToValidValue(
			AssignationStatement statement,
			IdentifierResolution leftResolution,
			EvaluableResolution rightResolution) {
		Boolean typesMatch =
				leftResolution
						.type()
						.or(() -> Optional.of(env.getDeclarationType(leftResolution.name())))
						.flatMap(
								value1 ->
										rightResolution
												.evaluatedType()
												.map(value2 -> value1 == value2))
						.orElseThrow(
								() -> new IllegalStateException("Cannot perform type comparison"));

		if (typesMatch) {
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot assign type "
							+ rightResolution.evaluatedType().get()
							+ " to "
							+ env.getDeclarationType(leftResolution.name()),
					statement.getStart(),
					statement.getEnd());
		}
	}

	private EvaluableResolution checkIdentifierDoesNotExist(
			AssignationStatement statement,
			IdentifierResolution leftResolution,
			EvaluableResolution rightResolution) {
		if (env.isVariableDeclared(leftResolution.name())) {
			return EvaluableResolution.failure(
					"Variable has already been declared", statement.getStart(), statement.getEnd());
		} else {
			return checkIsSimpleDeclaration(statement, leftResolution, rightResolution);
		}
	}

	private EvaluableResolution checkIsSimpleDeclaration(
			AssignationStatement statement,
			IdentifierResolution leftResolution,
			EvaluableResolution rightResolution) {
		boolean isSimpleDeclaration = rightResolution.evaluatedType().isEmpty();
		if (isSimpleDeclaration) {
			env.declareVariable(leftResolution.name(), leftResolution.type().get());
			return EvaluableResolution.emptySuccess();
		} else {
			return checkDeclaringWithValidValue(statement, leftResolution, rightResolution);
		}
	}

	private EvaluableResolution checkDeclaringWithValidValue(
			AssignationStatement statement,
			IdentifierResolution leftResolution,
			EvaluableResolution rightResolution) {
		Boolean typesMatch =
				leftResolution
						.type()
						.or(() -> Optional.of(env.getDeclarationType(leftResolution.name())))
						.flatMap(
								value1 ->
										rightResolution
												.evaluatedType()
												.map(value2 -> value1 == value2))
						.orElseThrow(
								() -> new IllegalStateException("Cannot perform type comparison"));

		if (typesMatch) {
			env.declareVariable(leftResolution.name(), leftResolution.type().get());
			return EvaluableResolution.emptySuccess();
		} else {
			return EvaluableResolution.failure(
					"Cannot assign value of type "
							+ rightResolution.evaluatedType().get()
							+ " to variable of type"
							+ leftResolution.type().get(),
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

		// TODO: improve (have no time due to shovel grabbing, man)

		List<EvaluableResolution> resolvedParameters =
				parameters.getParameters().stream()
						.map(astComponent -> astComponent.accept(this))
						.toList();

		Optional<EvaluableResolution> firstInvalidParameter =
				resolvedParameters.stream()
						.filter(resolution -> !resolution.result().isSuccessful())
						.findFirst();

		if (firstInvalidParameter.isPresent()) return firstInvalidParameter.get();

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
					new SemanticSuccess(),
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
