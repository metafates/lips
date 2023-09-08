package com.inno.lips.core.evaluator;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.sexpr.*;

import java.util.ArrayList;
import java.util.List;

class Builtin {
    private static Procedure println() {
        return new Procedure(arguments -> {
            List<String> strings = arguments.stream().map(LipsObject::toString).toList();

            System.out.println(String.join(" ", strings));
            return new LipsObject();
        });
    }

    private static Procedure printf() {
        return new Procedure(arguments -> {
            List<Object> literals = new ArrayList<>();

            if (arguments.isEmpty()) {
                throw new EvaluationException("printf expects at least one argument");
            }

            var iter = arguments.iterator();

            if (!(iter.next().sExpression() instanceof StringLiteral format)) {
                throw new EvaluationException("format must be a string");
            }

            while (iter.hasNext()) {
                var expr = iter.next().sExpression();

                if (!(expr instanceof Literal<?> literal)) {
                    throw new EvaluationException("printf supports literals only");
                }

                literals.add(literal.getValue());
            }

            System.out.printf(format.getValue(), literals.toArray());
            return new LipsObject();
        });
    }

    private static Procedure add() {
        return new Procedure(arguments -> {
            float sum = 0;

            for (var argument : arguments) {
                if (!(argument.sExpression() instanceof NumberLiteral numberLiteral)) {
                    // TODO: better message
                    throw new EvaluationException("invalid type");
                }

                sum += numberLiteral.getValue();
            }

            return new LipsObject(new NumberLiteral(Span.zero(), sum));
        });
    }

    private static Procedure sub() {
        return new Procedure(arguments -> {
            float sum = 0;

            for (var argument : arguments) {
                if (!(argument.sExpression() instanceof NumberLiteral numberLiteral)) {
                    // TODO: better message
                    throw new EvaluationException("invalid type");
                }

                sum -= numberLiteral.getValue();
            }

            return new LipsObject(new NumberLiteral(Span.zero(), sum));
        });
    }

    private static Procedure equal() {
        return new Procedure(arguments -> {
            if (arguments.isEmpty()) {
                throw new EvaluationException("expects at least 1 argument");
            }

            var iter = arguments.iterator();

            var prev = iter.next();

            while (iter.hasNext()) {
                var current = iter.next();

                if (!prev.sExpression().equals(current.sExpression())) {
                    return new LipsObject(new BooleanLiteral(Span.zero(), false));
                }

                prev = current;
            }

            return new LipsObject(new BooleanLiteral(Span.zero(), true));
        });
    }

    private static Procedure notEqual() {
        return new Procedure(arguments -> {
            var res = (BooleanLiteral) equal().apply(arguments).sExpression();

            return new LipsObject(new BooleanLiteral(res.span(), !res.getValue()));
        });
    }

    private static Procedure car() {
        return new Procedure(arguments -> {
            if (arguments.size() != 1) {
                // TODO: better message
                throw new EvaluationException("1 argument expected");
            }

            var argument = arguments.get(0).sExpression();

            if (!(argument instanceof Sequence sequence)) {
                throw new EvaluationException("sequence expected");
            }

            var elements = sequence.getElements();

            if (elements.isEmpty()) {
                throw new EvaluationException("at least one element is expected");
            }

            return new LipsObject(elements.get(0));
        });
    }

    private static Procedure cdr() {
        return new Procedure(arguments -> {
            if (arguments.size() != 1) {
                // TODO: better message
                throw new EvaluationException("1 argument expected");
            }

            var argument = arguments.get(0).sExpression();

            if (!(argument instanceof Sequence sequence)) {
                throw new EvaluationException("sequence expected");
            }

            var elements = sequence.getElements();

            if (elements.isEmpty()) {
                throw new EvaluationException("at least one element is expected");
            }

            if (elements.size() == 1) {
                return new LipsObject(new Sequence(Span.zero()));
            }

            var newSequence = new Sequence(Span.zero(), elements.subList(1, elements.size()));

            return new LipsObject(newSequence);
        });
    }

    public Scope scope() {
        var scope = new Scope();

        scope.put("println", println());
        scope.put("printf", printf());
        scope.put("+", add());
        scope.put("-", sub());
        scope.put("=", equal());
        scope.put("not=", notEqual());
        scope.put("car", car());
        scope.put("cdr", cdr());

        return scope;
    }
}
