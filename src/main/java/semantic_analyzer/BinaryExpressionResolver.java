package semantic_analyzer;

import model.AstComponent;
import model.BinaryExpression;
import model.BinaryOperator;
import model.DeclarationType;
import model.Literal;

import java.util.Collections;
import java.util.Map;

public class BinaryExpressionResolver implements Resolver {

    //TODO: may move placeholders to another file, or may actually
    // replace the usage of placeholder literals by another
    // mechanism, such as a ResolvedType class or sth of the sort
    public static final int NUMBER_PLACEHOLDER = 1;
    public static final String STRING_PLACEHOLDER = "";

    @Override
    public Resolution resolve(
            AstComponent ast, Map<String, DeclarationType> previousDeclarations,
            Map<Class<? extends AstComponent>, Resolver> resolvers
    ) {
        var binaryExpression = (BinaryExpression) ast;

        var rightResolution =
                resolvers
                        .get(binaryExpression.getRightComponent().getClass())
                        .resolve(binaryExpression.getRightComponent(), previousDeclarations, resolvers);

        if (rightResolution.result().isFailure()) {
            return rightResolution;
        }

        var leftResolution =
                resolvers
                        .get(binaryExpression.getRightComponent().getClass())
                        .resolve(binaryExpression.getRightComponent(), previousDeclarations, resolvers);

        if (leftResolution.result().isFailure()) {
            return leftResolution;
        }

        Literal<?> rightLiteral = (Literal<?>) rightResolution.resolvedAst();
        Literal<?> leftLiteral = (Literal<?>) leftResolution.resolvedAst();

        if (binaryExpression.getOperator() == BinaryOperator.SUM) {
            if (bothLiteralsAreNumbers(rightLiteral, leftLiteral)) {
                return new Resolution(
                        new SemanticSuccess(),
                        new Literal<Number>(NUMBER_PLACEHOLDER),
                        Collections.emptyMap()
                );
            } else {
                return new Resolution(
                        new SemanticSuccess(),
                        new Literal<String>(STRING_PLACEHOLDER),
                        Collections.emptyMap()
                );
            }
        }

        else {
            if (literalsHaveDifferentTypes(rightLiteral, leftLiteral)) {
                return Resolution.failure(
                        "Cannot perform operation because types are incompatible: "
                                + rightLiteral.getValue().getClass() + " "
                                + getSymbol(binaryExpression.getOperator()) + " "
                                + leftLiteral.getValue().getClass() + " "
                );
            } else {
                return new Resolution(
                        new SemanticSuccess(),
                        leftLiteral,
                        Collections.emptyMap()
                );
            }
        }
    }

    private String getSymbol(BinaryOperator operator) {
        // TODO: could move to BinaryOperator
        switch (operator) {
            case SUM:
                return "+";
            case SUBTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            default:
                // Unreachable
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean literalsHaveDifferentTypes(Literal<?> rightLiteral, Literal<?> leftLiteral) {
        return rightLiteral.getValue().getClass() != leftLiteral.getValue().getClass();
    }

    private static boolean bothLiteralsAreNumbers(Literal<?> rightLiteral, Literal<?> leftLiteral) {
        return rightLiteral.getValue() instanceof Number && leftLiteral.getValue() instanceof Number;
    }
}
