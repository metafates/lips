package com.inno.lips.core.evaluator;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.sexpr.*;

import java.util.ArrayList;
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
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

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
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            var iter = arguments.iterator();
            var first = iter.next();

            if (!(first.sExpression() instanceof NumberLiteral number)) {
                throw new TypeException(frame, "number", first);
            }

            float res = number.getValue();

            while (iter.hasNext()) {
                var argument = iter.next();
                if (!(argument.sExpression() instanceof NumberLiteral sub)) {
                    throw new TypeException(frame, "number", argument);
                }

                res -= sub.getValue();
            }

            return new LipsObject(new NumberLiteral(new Span(), res));
        });
    }

    private static Procedure times() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            float product = 1;

            for (var argument : arguments) {
                if (!(argument.sExpression() instanceof NumberLiteral numberLiteral)) {
                    throw new TypeException(frame, "number", argument);
                }

                product *= numberLiteral.getValue();
            }

            return new LipsObject(new NumberLiteral(new Span(), product));
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

    private static Procedure cons() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            var left = arguments.get(0).sExpression();
            var right = arguments.get(1).sExpression();

            List<SExpression> list = new ArrayList<>();

            list.add(left);


            if (right instanceof Sequence sequence) {
                list.addAll(sequence.getElements());
            } else if (!(right instanceof Nil)) {
                throw new TypeException(frame, "sequence", arguments.get(1));
            }


            return new LipsObject(new Sequence(left.span().join(right), list));
        }));
    }

    private static Procedure eval() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument instanceof Procedure) {
                throw new TypeException(frame, "list or atom", "procedure");
            }

            // TODO: pass valid env
            return Evaluator.evaluate(frame, new Environment(), argument.sExpression());
        });
    }

    private static Procedure and() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            for (var argument : arguments) {
                if (!argument.sExpression().asBoolean()) {
                    return new LipsObject(new BooleanLiteral(new Span(), false));
                }
            }

            return new LipsObject(new BooleanLiteral(new Span(), true));
        }));
    }

    private static Procedure or() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            for (var argument : arguments) {
                if (argument.sExpression().asBoolean()) {
                    return new LipsObject(new BooleanLiteral(new Span(), true));
                }
            }

            return new LipsObject(new BooleanLiteral(new Span(), false));
        }));
    }

    public Procedure isList() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument.sExpression() instanceof Sequence) {
                return new LipsObject(new BooleanLiteral(new Span(), true));
            }

            return new LipsObject(new BooleanLiteral(new Span(), false));
        }));
    }

    public Procedure isNumber() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument.sExpression() instanceof NumberLiteral) {
                return new LipsObject(new BooleanLiteral(new Span(), true));
            }

            return new LipsObject(new BooleanLiteral(new Span(), false));
        }));
    }

    public Procedure isBool() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument.sExpression() instanceof BooleanLiteral) {
                return new LipsObject(new BooleanLiteral(new Span(), true));
            }

            return new LipsObject(new BooleanLiteral(new Span(), false));
        }));
    }

    public Procedure isNil() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument.sExpression() instanceof Nil) {
                return new LipsObject(new BooleanLiteral(new Span(), true));
            }

            return new LipsObject(new BooleanLiteral(new Span(), false));
        }));
    }

    public Procedure isSymbol() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument.sExpression() instanceof Symbol) {
                return new LipsObject(new BooleanLiteral(new Span(), true));
            }

            return new LipsObject(new BooleanLiteral(new Span(), false));
        }));
    }

    public Environment scope() {
        var scope = new Environment();

        scope.put("println", println());
        scope.put("+", add());
        scope.put("-", sub());
        scope.put("*", times());
        scope.put("=", equal());
        scope.put("not=", notEqual());
        scope.put("car", car());
        scope.put("cdr", cdr());
        scope.put("cons", cons());
        scope.put("eval", eval());
        scope.put("and", and());
        scope.put("not", not());
        scope.put("or", or());
        scope.put("list?", isList());
        scope.put("number?", isNumber());
        scope.put("bool?", isBool());
        scope.put("nil?", isNil());
        scope.put("symbol?", isSymbol());

        return scope;
    }
}
