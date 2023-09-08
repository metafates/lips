package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.sexpr.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    public static LipsObject evaluate(Scope scope, SExpression sExpression) throws EvaluationException {
        if (sExpression instanceof Atom atom) {
            return evaluateAtom(scope, atom);
        }

        if (sExpression instanceof Sequence sequence) {
            return evaluateSequence(scope, sequence);
        }

        return null;
    }

    private static LipsObject evaluateSequence(Scope scope, Sequence sequence) throws EvaluationException {
        if (sequence instanceof SpecialForm specialForm) {
            return evaluateSpecialForm(scope, specialForm);
        }

        var elements = sequence.getElements();

        if (elements.isEmpty()) {
            return new LipsObject();
        }

        var iter = elements.iterator();
        var head = evaluate(scope, iter.next());

        if (!(head instanceof Procedure procedure)) {
            throw new NotCallableException();
        }

        List<LipsObject> arguments = new ArrayList<>();

        while (iter.hasNext()) {
            arguments.add(evaluate(scope, iter.next()));
        }

        return procedure.apply(arguments);
    }

    private static LipsObject evaluateAtom(Scope scope, Atom atom) throws EvaluationException {
        if (atom instanceof Literal<?> literal) {
            return new LipsObject(literal);
        }

        if (atom instanceof Symbol symbol) {
            return scope.get(symbol);
        }

        return null;
    }

    private static LipsObject evaluateSpecialForm(Scope scope, SpecialForm specialForm) throws EvaluationException {
        if (specialForm instanceof Quote quote) {
            return new LipsObject(quote.getBody());
        }

        if (specialForm instanceof Lambda lambda) {
            return new Procedure(scope.inner(), lambda);
        }

        if (specialForm instanceof Func func) {
            scope.put(func.getIdentifier(), new Procedure(scope.inner(), func));

            return new LipsObject();
        }

        if (specialForm instanceof SetQ setQ) {
            var value = evaluate(scope, setQ.getValue());
            scope.put(setQ.getSymbol(), value);

            return new LipsObject();
        }

        if (specialForm instanceof Cond cond) {
            var condition = cond.getCondition();

            if (evaluate(scope, condition).sExpression().asBoolean()) {
                return evaluate(scope, cond.getThenBranch());
            }

            return evaluate(scope, cond.getElseBranch());
        }

        if (specialForm instanceof While whileForm) {
            var condition = whileForm.getCondition();
            var body = whileForm.getBody();

            while (evaluate(scope, condition).sExpression().asBoolean()) {
                for (var expression : body) {
                    evaluate(scope, expression);
                }
            }

            return new LipsObject();
        }

        return null;
    }
}
