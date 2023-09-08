package com.inno.lips.core.evaluator;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.sexpr.BooleanLiteral;
import com.inno.lips.core.parser.sexpr.NumberLiteral;

import java.util.List;

class Builtin {
    private static Procedure println() {
        return new Procedure(arguments -> {
            List<String> strings = arguments.stream().map(LipsObject::toString).toList();

            System.out.println(String.join(" ", strings));
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

    public Scope scope() {
        var scope = new Scope();

        scope.put("println", println());
        scope.put("+", add());
        scope.put("-", sub());
        scope.put("=", equal());
        scope.put("not=", notEqual());

        return scope;
    }
}
