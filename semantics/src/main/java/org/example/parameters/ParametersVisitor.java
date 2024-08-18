package org.example.parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.example.Result;
import org.example.SemanticSuccess;
import org.example.ast.BinaryExpression;
import org.example.ast.Conditional;
import org.example.ast.Declaration;
import org.example.ast.DeclarationType;
import org.example.ast.EvaluableComponent;
import org.example.ast.Identifier;
import org.example.ast.Literal;
import org.example.ast.Parameters;
import org.example.ast.StatementBlock;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.evaluables.EvaluableResolution;
import org.example.evaluables.EvaluableVisitor;

public class ParametersVisitor implements AstComponentVisitor<ParametersResolution> {
	private EvaluableVisitor evaluableVisitor;

	public void setEvaluableVisitor(EvaluableVisitor evaluableVisitor) {
		this.evaluableVisitor = evaluableVisitor;
	}

	@Override
	public ParametersResolution visit(BinaryExpression expression) {
		return null;
	}

	@Override
	public ParametersResolution visit(Conditional conditional) {
		return null;
	}

	@Override
	public ParametersResolution visit(IfStatement ifStatement) {
		return null;
	}

	@Override
	public ParametersResolution visit(Literal<?> literal) {
		return null;
	}

	@Override
	public ParametersResolution visit(Parameters parameters) {
		List<DeclarationType> types = new ArrayList<>();

		for (EvaluableComponent component : parameters.getParameters()) {
			EvaluableResolution resolution = component.accept(evaluableVisitor);
			Result result = resolution.result();

			if (!result.isSuccessful())
				return new ParametersResolution(result, Collections.emptyList());
			else types.add(resolution.evaluatedType().get());
		}

		return new ParametersResolution(new SemanticSuccess(), types);
	}

	@Override
	public ParametersResolution visit(AssignationStatement statement) {
		return null;
	}

	@Override
	public ParametersResolution visit(Declaration statement) {
		return null;
	}

	@Override
	public ParametersResolution visit(FunctionCallStatement statement) {
		return null;
	}

	@Override
	public ParametersResolution visit(StatementBlock statementBlock) {
		return null;
	}

	@Override
	public ParametersResolution visit(Identifier identifier) {
		return null;
	}
}
