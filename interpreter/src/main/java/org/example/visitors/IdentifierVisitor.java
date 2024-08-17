package org.example.visitors;

import org.example.InterpreterState;
import org.example.Variable;
import org.example.VariableType;
import org.example.ast.*;
import org.example.ast.statement.AssignationStatement;
import org.example.ast.statement.FunctionCallStatement;
import org.example.ast.statement.IfStatement;
import org.example.ast.visitor.AstComponentVisitor;
import org.example.ast.visitor.IdentifierComponentVisitor;

public class IdentifierVisitor implements IdentifierComponentVisitor<String> {
	private final InterpreterState state;

	public IdentifierVisitor(InterpreterState state) {
		this.state = state;
	}

	@Override
	public String visit(Identifier variableIdentifier) {
		return variableIdentifier.getName();
	}

	@Override
	public String visit(Declaration declaration) {
		DeclarationType declarationType = declaration.getType();
		String name = declaration.getName();
		switch (declarationType) {
			case NUMBER -> createNumericVariable(name);
			case STRING -> createStringVariable(name);
			default ->
					throw new IllegalArgumentException(
							"Implement declaration type: " + declarationType);
		}
		return name;
	}

	private void createNumericVariable(String name) {
		state.addNumericVariable(new Variable<>(VariableType.NUMBER, name, null));
	}

	private void createStringVariable(String name) {
		state.addStringVariable(new Variable<>(VariableType.STRING, name, null));
	}
}
