package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.ast.Element;
import com.inno.lips.core.parser.ast.Lambda;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Procedure extends Lambda {
    private final Scope scope;
    private final Function<List<Element>, Optional<Element>> raw;

    public Procedure(Lambda lambda, Scope scope) {
        super(lambda.getArguments(), lambda.getBody(), lambda.getSyntaxObject());

        this.scope = scope;
        this.raw = null;
    }

    public Procedure(Function<List<Element>, Optional<Element>> raw) {
        super(null, null, null);

        this.raw = raw;
        this.scope = new Scope();
    }

    public Optional<Element> call(List<Element> arguments) throws RuntimeException {
        if (raw != null) {
            return raw.apply(arguments);
        }

        var inner = scope.inner();

        if (arguments.size() != getArguments().size()) {
            throw new ArityMismatchException("");
        }

        for (int i = 0; i < arguments.size(); i++) {
            String name = getArguments().get(i).identifier();

            inner.set(name, arguments.get(i));
        }

        return Evaluator.evaluate(getBody(), inner);
    }
}
