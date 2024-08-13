package org.example.resolution_validators;

import org.example.Environment;
import org.example.ast.BinaryExpression;
import org.example.evaluables.EvaluableResolution;
import org.example.externalization.Language;

public class IsOperationValid extends ConditionalValidator {
    private final Language language;
    private final EvaluableResolution leftResolution;
    private final EvaluableResolution rightResolution;
    private final BinaryExpression expression;

    public IsOperationValid(
            Language language,
            EvaluableResolution leftResolution,
            EvaluableResolution rightResolution,
            BinaryExpression expression,
            ResolutionValidator trueCaseValidator,
            ResolutionValidator falseCaseValidator
    ) {
        super(trueCaseValidator, falseCaseValidator);
        this.language = language;
        this.leftResolution = leftResolution;
        this.rightResolution = rightResolution;
        this.expression = expression;
    }

    @Override
    protected boolean meetsCondition(Environment environment) {
        return language.isOperationSupported(
                leftResolution.evaluatedType().get(),
                expression.getOperator(),
                rightResolution.evaluatedType().get()
        );
    }
}
