package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.element.Lambda;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Procedure {
    private final Scope scope;
    private final Lambda lambda;
    private final Function<List<Value>, Optional<Value>> function;

    public Procedure(Lambda lambda, Scope scope) {
        this.lambda = lambda;
        this.scope = scope;
        this.function = null;
    }

    public Procedure(Function<List<Value>, Optional<Value>> function) {
        this.function = function;
        this.scope = new Scope();
        this.lambda = null;
    }

    public Optional<Value> apply(List<Value> parameters) throws EvaluationException {
        if (function != null) {
            return function.apply(parameters);
        }

        assert lambda != null;

        var inner = scope.inner();

        if (parameters.size() != lambda.getParameters().size()) {
            throw new ArityMismatchException(parameters.size(), lambda.getParameters().size());
        }

        for (int i = 0; i < parameters.size(); i++) {
            String name = lambda.getParameters().get(i).name();
            inner.set(name, parameters.get(i));
        }


        return Evaluator.evaluate(lambda.getBody(), inner);
    }
}
