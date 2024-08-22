package org.example.evaluables;

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
import org.example.resolution_validators.AssigningToValidValue;
import org.example.resolution_validators.IdentifierExists;
import org.example.resolution_validators.IsDeclarationElseAssignation;
import org.example.resolution_validators.IsFunctionDeclared;
import org.example.resolution_validators.IsOperationValid;
import org.example.resolution_validators.IsResolutionSuccessful;
import org.example.resolution_validators.IsSimpleDeclaration;

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

		return new IsResolutionSuccessful(
						leftResolution,
						new IsResolutionSuccessful(
								rightResolution,
								new IsOperationValid(
										lang,
										leftResolution,
										rightResolution,
										expression,
										(env) ->
												new EvaluableResolution(
														new SemanticSuccess(),
														Optional.of(
																lang.getResolvedType(
																		leftResolution
																				.evaluatedType()
																				.get(),
																		expression.getOperator(),
																		rightResolution
																				.evaluatedType()
																				.get())),
														true,
														Optional.empty()),
										(env) ->
												EvaluableResolution.failure(
														"Cannot perform operation because types are incompatible: "
																+ rightResolution
																		.evaluatedType()
																		.get()
																+ " "
																+ getSymbol(
																		expression.getOperator())
																+ " "
																+ leftResolution
																		.evaluatedType()
																		.get()
																+ " ",
														expression.getStart(),
														expression.getEnd())),
								(env) -> rightResolution),
						(env) -> leftResolution)
				.analyze(env);
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
				literal.getValue() != null,
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

		return new IsResolutionSuccessful(
						rightResolution,
						new IsDeclarationElseAssignation(
								leftResolution,
								new IdentifierExists(
										leftResolution,
										(env) ->
												EvaluableResolution.failure(
														"Variable has already been declared",
														statement.getStart(),
														statement.getEnd()),
										new IsSimpleDeclaration(
												rightResolution,
												(env) -> {
													env.declareVariable(
															leftResolution.name(),
															leftResolution.type().get());
													return EvaluableResolution.emptySuccess();
												},
												new AssigningToValidValue(
														leftResolution,
														rightResolution,
														(env) -> {
															env.declareVariable(
																	leftResolution.name(),
																	leftResolution.type().get());
															return EvaluableResolution
																	.emptySuccess();
														},
														(env) ->
																EvaluableResolution.failure(
																		"Cannot assign value of type "
																				+ rightResolution
																						.evaluatedType()
																						.get()
																				+ " to variable of type"
																				+ leftResolution
																						.type()
																						.get(),
																		statement.getStart(),
																		statement.getEnd())))),
								new IdentifierExists(
										leftResolution,
										new AssigningToValidValue(
												leftResolution,
												rightResolution,
												(env) -> EvaluableResolution.emptySuccess(),
												(env) ->
														EvaluableResolution.failure(
																"Cannot assign type "
																		+ rightResolution
																				.evaluatedType()
																				.get()
																		+ " to "
																		+ env.getDeclarationType(
																				leftResolution
																						.name()),
																statement.getStart(),
																statement.getEnd())),
										(env) ->
												EvaluableResolution.failure(
														"Cannot assign non-existing identifier",
														statement.getStart(),
														statement.getEnd()))),
						(env) -> rightResolution)
				.analyze(env);
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

		List<DeclarationType> types =
				resolvedParameters.stream()
						.map(resolution -> resolution.evaluatedType().get())
						.toList();

		if (firstInvalidParameter.isPresent()) return firstInvalidParameter.get();

		String functionName = functionCallResolution.name();

		return new IsResolutionSuccessful(
						functionCallResolution,
						new IsFunctionDeclared(
								types,
								functionCallResolution,
								(env) -> EvaluableResolution.emptySuccess(),
								(env) ->
										EvaluableResolution.failure(
												"Cannot resolve function signature "
														+ functionName
														+ "("
														+ types
														+ ").",
												statement.getStart(),
												statement.getEnd())),
						(env) -> EvaluableResolution.castFrom(functionCallResolution))
				.analyze(env);
	}

	@Override
	public EvaluableResolution visit(StatementBlock statementBlock) {
		return null;
	}

	@Override
	public EvaluableResolution visit(Identifier identifier) {
		return new IdentifierExists(
						identifier,
						(env) ->
								new EvaluableResolution(
										new SemanticSuccess(),
										Optional.of(env.getDeclarationType(identifier.getName())),
										false,
										Optional.of(identifier.getName())),
						(env) ->
								new EvaluableResolution(
										new SemanticFailure(
												"Cannot find identifier " + identifier.getName(),
												Optional.of(identifier.getStart()),
												Optional.of(identifier.getEnd())),
										Optional.empty(),
										false,
										Optional.of(identifier.getName())))
				.analyze(env);
	}
}
