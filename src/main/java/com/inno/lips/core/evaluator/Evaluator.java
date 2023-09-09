package com.inno.lips.core.evaluator;

import com.inno.lips.core.parser.sexpr.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    public static LipsObject evaluate(Frame frame, Environment environment, SExpression sExpression) throws EvaluationException {
        if (sExpression instanceof Atom atom) {
            return evaluateAtom(frame, environment, atom);
        }

        if (sExpression instanceof Sequence sequence) {
            return evaluateSequence(frame, environment, sequence);
        }

        throw new UnsupportedOperationException(frame);
    }

    private static LipsObject evaluateSequence(Frame frame, Environment environment, Sequence sequence) throws EvaluationException {
        if (sequence instanceof SpecialForm specialForm) {
            return evaluateSpecialForm(frame, environment, specialForm);
        }

        var elements = sequence.getElements();

        if (elements.isEmpty()) {
            return new LipsObject();
        }

        var iter = elements.iterator();
        var toCall = iter.next();
        var head = evaluate(frame.inner(toCall.span(), "list"), environment, toCall);

        if (!(head instanceof Procedure procedure)) {
            throw new NotCallableException(frame);
        }

        List<LipsObject> arguments = new ArrayList<>();

        while (iter.hasNext()) {
            var next = iter.next();
            arguments.add(evaluate(frame.inner(next.span(), "list"), environment, next));
        }

        return procedure.apply(frame.inner(sequence.span(), "call"), arguments);
    }

    private static LipsObject evaluateAtom(Frame frame, Environment environment, Atom atom) throws EvaluationException {
        if (atom instanceof Literal<?> literal) {
            return new LipsObject(literal);
        }

        if (atom instanceof Symbol symbol) {
            return environment.get(frame, symbol);
        }

        if (atom instanceof Nil nil) {
            return new LipsObject(nil);
        }

        throw new UnsupportedOperationException(frame);
    }

    private static LipsObject evaluateSpecialForm(Frame frame, Environment environment, SpecialForm specialForm) throws EvaluationException {
        if (specialForm instanceof Quote quote) {
            return new LipsObject(quote.getBody());
        }

        if (specialForm instanceof Lambda lambda) {
            return new Procedure(frame.inner(lambda.span(), "lambda"), environment.inner(), lambda);
        }

        if (specialForm instanceof Func func) {
            var identifier = func.getIdentifier();
            var procedure = new Procedure(frame.inner(func.span(), identifier.getName()), environment.inner(), func);
            environment.put(func.getIdentifier(), procedure);

            return new LipsObject();
        }

        if (specialForm instanceof SetQ setQ) {
            var symbol = setQ.getSymbol();
            var value = evaluate(frame.inner(symbol.span(), symbol.getName()), environment, setQ.getValue());
            environment.put(symbol, value);

            return new LipsObject();
        }

        if (specialForm instanceof If anIf) {
            var predicate = anIf.getPredicate();
            var condFrame = frame.inner(predicate.span(), "cond");

            if (evaluate(condFrame, environment, predicate).sExpression().asBoolean()) {
                var branch = anIf.getThenBranch();
                return evaluate(condFrame.inner(branch.span(), "then"), environment, branch);
            }

            var branch = anIf.getElseBranch();
            return evaluate(frame.inner(branch.span(), "else"), environment, branch);
        }

        if (specialForm instanceof Cond cond) {
            var branches = cond.getBranches();

            var condFrame = frame.inner(cond.span(), "cond");
            for (var branch : branches) {
                var predicate = branch.predicate();
                var body = branch.body();
                var branchFrame = condFrame.inner(predicate.span(), "branch-predicate");

                if (evaluate(branchFrame, environment, predicate).sExpression().asBoolean()) {
                    var bodyFrame = branchFrame.inner(body.span(), "branch-body");
                    return evaluate(bodyFrame, environment, body);
                }
            }
        }

        if (specialForm instanceof While whileForm) {
            var predicate = whileForm.getPredicate();
            var body = whileForm.getBody();

            var whileFrame = frame.inner(predicate.span(), "while");
            while (evaluate(whileFrame, environment, predicate).sExpression().asBoolean()) {
                for (var expression : body) {
                    evaluate(whileFrame.inner(expression.span(), "while-body"), environment, expression);
                }
            }

            return new LipsObject();
        }

        throw new UnsupportedOperationException(frame);
    }
}
