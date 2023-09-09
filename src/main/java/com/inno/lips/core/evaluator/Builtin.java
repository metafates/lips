package com.inno.lips.core.evaluator;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.sexpr.BooleanLiteral;
import com.inno.lips.core.parser.sexpr.NumberLiteral;
import com.inno.lips.core.parser.sexpr.Sequence;

import java.util.List;

class Builtin {
    private static Procedure println() {
        return new Procedure((frame, arguments) -> {
            List<String> strings = arguments.stream().map(LipsObject::toString).toList();

            System.out.println(String.join(" ", strings));
            return new LipsObject();
        });
    }

    private static Procedure add() {
        return new Procedure((frame, arguments) -> {
            float sum = 0;

            for (var argument : arguments) {
                if (!(argument.sExpression() instanceof NumberLiteral numberLiteral)) {
                    throw new TypeException(frame, "number", argument);
                }

                sum += numberLiteral.getValue();
            }

            return new LipsObject(new NumberLiteral(new Span(), sum));
        });
    }

    private static Procedure sub() {
        return new Procedure((frame, arguments) -> {
            float sum = 0;

            for (var argument : arguments) {
                if (!(argument.sExpression() instanceof NumberLiteral numberLiteral)) {
                    throw new TypeException(frame, "number", argument);
                }

                sum -= numberLiteral.getValue();
            }

            return new LipsObject(new NumberLiteral(new Span(), sum));
        });
    }

    private static Procedure equal() {
        return new Procedure((frame, arguments) -> {
            if (arguments.isEmpty()) {
                throw new ArityMismatchException(frame, 1, 0);
            }

            var iter = arguments.iterator();

            var prev = iter.next();

            while (iter.hasNext()) {
                var current = iter.next();

                if (!prev.sExpression().equals(current.sExpression())) {
                    return new LipsObject(new BooleanLiteral(new Span(), false));
                }

                prev = current;
            }

            return new LipsObject(new BooleanLiteral(new Span(), true));
        });
    }

    private static Procedure notEqual() {
        return new Procedure((frame, arguments) -> {
            var res = (BooleanLiteral) equal().apply(frame, arguments).sExpression();

            return new LipsObject(new BooleanLiteral(res.span(), !res.getValue()));
        });
    }

    private static Procedure car() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                // TODO: better message
                throw new EvaluationException(frame, "1 argument expected");
            }

            var argument = arguments.get(0);

            if (!(argument.sExpression() instanceof Sequence sequence)) {
                throw new TypeException(frame, "sequence", argument);
            }

            var elements = sequence.getElements();

            if (elements.isEmpty()) {
                throw new ArityMismatchException(frame, 1, 0);
            }

            return new LipsObject(elements.get(0));
        });
    }

    private static Procedure cdr() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                // TODO: better message
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (!(argument.sExpression() instanceof Sequence sequence)) {
                throw new TypeException(frame, "sequence", argument);
            }

            var elements = sequence.getElements();

            if (elements.isEmpty()) {
                throw new ArityMismatchException(frame, 1, 0);
            }

            if (elements.size() == 1) {
                return new LipsObject(new Sequence(new Span()));
            }

            var newSequence = new Sequence(new Span(), elements.subList(1, elements.size()));

            return new LipsObject(newSequence);
        });
    }

    private static Procedure not() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0).sExpression();

            return new LipsObject(new BooleanLiteral(argument.span(), !argument.asBoolean()));
        });
    }

    public Environment scope() {
        var scope = new Environment();

        scope.put("println", println());
        scope.put("+", add());
        scope.put("-", sub());
        scope.put("=", equal());
        scope.put("not=", notEqual());
        scope.put("car", car());
        scope.put("cdr", cdr());
        scope.put("not", not());

        return scope;
    }
}
