package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.ast.*;

import java.util.ArrayList;
import java.util.Optional;

public class Evaluator {
    public static Optional<Element> evaluate(Element element, Scope scope) throws RuntimeException {
        if (element instanceof Symbol symbol) {
            // TODO
            return Optional.of(scope.get(symbol.identifier()).orElseThrow());
        }

        if (element instanceof Literal<?> literal) {
            return Optional.of(literal);
        }

        if (element instanceof Set set) {
            // TODO
            var evaluated = evaluate(set.getElement(), scope.inner()).orElseThrow();
            scope.set(set.getSymbol().identifier(), evaluated);
            return Optional.empty();
        }

        if (element instanceof Lambda lambda) {
            return Optional.of(new Procedure(lambda, scope));
        }

        if (element instanceof List list) {
            var arguments = list.getArguments().iterator();

            // TODO: handle empty list and other improvements
            var first = evaluate(arguments.next(), scope.inner()).orElseThrow();

            if (!(first instanceof Procedure procedure)) {
                // TODO: better message
                throw new RuntimeException("procedure expected");
            }

            var values = new ArrayList<Element>();
            arguments.forEachRemaining(values::add);

            return procedure.call(values);
        }

        throw new RuntimeException("idk");
    }

    public static Optional<Element> evaluate(Element element) throws RuntimeException {
        return evaluate(element, new Scope());
    }
}
