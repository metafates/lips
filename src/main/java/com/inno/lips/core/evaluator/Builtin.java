package com.inno.lips.core.evaluator;

import com.inno.lips.core.evaluator.object.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class Builtin {
    private static Procedure println() {
        return new Procedure((frame, arguments) -> {
            List<String> strings = arguments.stream().map(LipsObject::toString).toList();

            System.out.println(String.join(" ", strings));
            return LipsNil.instance;
        });
    }

    private static Procedure numericOp(int minArity, Function<List<Float>, Float> op) {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() < minArity) {
                throw new ArityMismatchException(frame, minArity, arguments.size());
            }

            List<Float> numbers = new ArrayList<>();

            for (var argument : arguments) {
                if (!(argument instanceof LipsNumber number)) {
                    throw new TypeException(frame, "number", argument);
                }

                numbers.add(number.getValue());
            }

            return new LipsNumber(op.apply(numbers));
        }));
    }

    private static Procedure add() {
        return numericOp(2, nums -> nums.stream().reduce(0f, Float::sum));
    }

    private static Procedure sub() {
        return numericOp(1, nums -> {
            if (nums.size() == 1) {
                return -1 * nums.get(0);
            }

            var iter = nums.iterator();
            float res = iter.next();

            while (iter.hasNext()) {
                res -= iter.next();
            }

            return res;
        });
    }

    private static Procedure times() {
        return numericOp(2, nums -> nums.stream().reduce(1f, (a, b) -> a * b));
    }

    private static Procedure divide() {
        return numericOp(2, nums -> {
            var iter = nums.iterator();
            var res = iter.next();

            while (iter.hasNext()) {
                res /= iter.next();
            }

            return res;
        });
    }

    private static Procedure pow() {
        return numericOp(2, nums -> {
            var iter = nums.iterator();
            var res = iter.next();

            while (iter.hasNext()) {
                res = (float) Math.pow(res, iter.next());
            }

            return res;
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

                if (!prev.equals(current)) {
                    return new LipsBoolean(false);
                }

                prev = current;
            }

            return new LipsBoolean(true);
        });
    }

    private static Procedure notEqual() {
        return new Procedure((frame, arguments) -> {
            var res = (LipsBoolean) equal().apply(frame, arguments);
            return res.inversed();
        });
    }

    private static Procedure head() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                // TODO: better message
                throw new EvaluationException(frame, "1 argument expected");
            }

            var argument = arguments.get(0);

            if (!(argument instanceof LipsSeq seq)) {
                throw new TypeException(frame, "sequence", argument);
            }

            var elements = seq.getElements();

            if (elements.isEmpty()) {
                throw new ArityMismatchException(frame, 1, 0);
            }

            return elements.get(0);
        });
    }

    private static Procedure tail() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                // TODO: better message
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (!(argument instanceof LipsSeq seq)) {
                throw new TypeException(frame, "sequence", argument);
            }

            var elements = seq.getElements();

            if (elements.isEmpty()) {
                throw new ArityMismatchException(frame, 1, 0);
            }

            if (elements.size() == 1) {
                return new LipsSeq();
            }

            return new LipsSeq(elements.subList(1, elements.size()));
        });
    }

    private static Procedure not() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            return new LipsBoolean(arguments.get(0).asBoolean());
        });
    }

    private static Procedure cons() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            var left = arguments.get(0);
            var right = arguments.get(1);

            List<LipsObject> elements = new ArrayList<>();

            elements.add(left);


            if (right instanceof LipsSeq seq) {
                elements.addAll(seq.getElements());
            } else if (!(right instanceof LipsNil)) {
                throw new TypeException(frame, "sequence", arguments.get(1));
            }


            return new LipsSeq(elements);
        }));
    }

    private static Procedure eval() {
        return new Procedure((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            return arguments.get(0).evaluate(frame, new Environment());
        });
    }

    private static Procedure and() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            for (var argument : arguments) {
                if (!argument.asBoolean()) {
                    return new LipsBoolean(false);
                }
            }

            return new LipsBoolean(true);
        }));
    }

    private static Procedure or() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() < 2) {
                throw new ArityMismatchException(frame, 2, arguments.size());
            }

            for (var argument : arguments) {
                if (argument.asBoolean()) {
                    return new LipsBoolean(true);
                }
            }

            return new LipsBoolean(false);
        }));
    }

    public Procedure isList() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument instanceof LipsSeq) {
                return new LipsBoolean(true);
            }

            return new LipsBoolean(false);
        }));
    }

    public Procedure isNumber() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument instanceof LipsNumber) {
                return new LipsBoolean(true);
            }

            return new LipsBoolean(false);
        }));
    }

    public Procedure isBool() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument instanceof LipsBoolean) {
                return new LipsBoolean(true);
            }

            return new LipsBoolean(false);
        }));
    }

    public Procedure isNil() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument instanceof LipsNil) {
                return new LipsBoolean(true);
            }

            return new LipsBoolean(false);
        }));
    }

    public Procedure isSymbol() {
        return new Procedure(((frame, arguments) -> {
            if (arguments.size() != 1) {
                throw new ArityMismatchException(frame, 1, arguments.size());
            }

            var argument = arguments.get(0);

            if (argument instanceof LipsSymbol) {
                return new LipsBoolean(true);
            }

            return new LipsBoolean(false);
        }));
    }

    public Environment scope() {
        var scope = new Environment();

        scope.put("println", println());
        scope.put("+", add());
        scope.put("-", sub());
        scope.put("*", times());
        scope.put("/", divide());
        scope.put("pow", pow());
        scope.put("=", equal());
        scope.put("not=", notEqual());
        scope.put("car", head());
        scope.put("cdr", tail());
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
