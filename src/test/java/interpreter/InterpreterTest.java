package interpreter;

import model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InterpreterTest {
    @Test
    public void testTest() {
        Interpreter interpreter = new PrintScriptInterpreter();
        AstComponent ast1 = new Assignation(new Declaration(DeclarationType.STRING, "a"), new BinaryExpression(BinaryOperator.SUM, new Literal<>(3.0), new Literal<>("hola")));
        AstComponent ast2 = new Assignation(new Identifier("a", IdentifierType.VARIABLE), new Literal<>("hello"));
        interpreter.interpret(List.of(ast1, ast2));

    }
}
