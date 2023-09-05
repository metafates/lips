package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.element.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Evaluator {
    public static Optional<Value> evaluate(Element element, Scope scope) throws EvaluationException {
        return switch (element) {
            case Symbol symbol -> scope.get(symbol.name());
            case ListNode list -> {
                List<Element> elements = list.getElements();
                // TODO: check if list is empty
                var iter = elements.iterator();
                var op = iter.next();

                yield switch (op) {
                    case Cond cond -> {
                        // TODO
                        var test = evaluate(cond.getTestExpr(), scope.inner()).orElseThrow();

                        if (!(test.element().orElseThrow() instanceof BooleanLiteral booleanLiteral)) {
                            throw new EvaluationException("boolean expected");
                        }

                        if (booleanLiteral.getValue()) {
                            yield evaluate(cond.getThenExpr(), scope.inner());
                        }

                        yield evaluate(cond.getElseExpr(), scope.inner());
                    }
                    case Set set -> {
                        // UNREACHABLE
                        scope.set(set.getSymbol().name(), evaluate(set.getElement(), scope).orElseThrow());
                        yield Optional.empty();
                    }
                    case Lambda lambda -> {
                        var procedure = new Procedure(lambda, scope.inner());
                        yield Optional.of(new Value(procedure));
                    }
                    default -> {
                        // TODO
                        var proc = evaluate(op, scope.inner()).orElseThrow();
                        Procedure result = proc.procedure().orElseThrow();

                        List<Value> arguments = new ArrayList<>();
                        while (iter.hasNext()) {
                            // TODO
                            Value argument = evaluate(iter.next(), scope.inner()).orElseThrow();

                            arguments.add(argument);
                        }

                        yield result.apply(arguments);
                    }
                };
            }
            default -> Optional.of(new Value(element));
        };
    }
}
