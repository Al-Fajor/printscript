package org.example.visitors;

import org.example.InterpreterState;
import org.example.Variable;
import org.example.VariableType;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.Visitor;

public class IdentifierVisitor implements Visitor<String> {
	private final InterpreterState state;

	public IdentifierVisitor(InterpreterState state) {
		this.state = state;
	}

	@Override
	public String visit(VariableIdentifier variableIdentifier) {
		return variableIdentifier.getName();
	}

	@Override
	public String visit(Declaration declaration) {
		DeclarationType declarationType = declaration.getType();
		String name = declaration.getName();
		switch (declarationType) {
			case NUMBER ->
				createNumericVariable(name);
			case STRING ->
				createStringVariable(name);
			default ->
				throw new IllegalArgumentException("Implement declaration type: " + declarationType);
		}
		return name;
	}

	private void createNumericVariable(String name) {
		state.addNumericVariable(new Variable<>(VariableType.NUMBER, name, null));
	}

	private void createStringVariable(String name) {
		state.addStringVariable(new Variable<>(VariableType.STRING, name, null));
	}

	@Override
	public String visit(Literal<?> literal) {
		return "";
	}

	@Override
	public String visit(BinaryExpression expression) {
		return "";
	}

	@Override
	public String visit(Conditional conditional) {
		return "";
	}

	@Override
	public String visit(IfStatement ifStatement) {
		return "";
	}

	@Override
	public String visit(Parameters parameters) {
		return "";
	}

	@Override
	public String visit(AssignationStatement statement) {
		return "";
	}

	@Override
	public String visit(FunctionCallStatement statement) {
		return "";
	}

	@Override
	public String visit(StatementBlock statementBlock) {
		return "";
	}
}
