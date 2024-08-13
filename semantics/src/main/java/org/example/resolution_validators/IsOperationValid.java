package org.example.resolution_validators;

import org.example.Environment;
import org.example.ast.BinaryExpression;
import org.example.evaluables.Resolution;
import org.example.externalization.Language;

public class IsOperationValid extends ConditionalValidator {
    private final Language language;
    private final Resolution leftResolution;
    private final Resolution rightResolution;
    private final BinaryExpression expression;

    public IsOperationValid(
            Language language,
            Resolution leftResolution,
            Resolution rightResolution,
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
