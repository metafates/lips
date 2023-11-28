package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;
import com.inno.lips.core.parser.sexpr.*;

public final class LipsSpecial extends LipsObject {
    private final SpecialForm specialForm;

    public LipsSpecial(SpecialForm specialForm) {
        this.specialForm = specialForm;
    }

    public SpecialForm getSpecialForm() {
        return specialForm;
    }

    @Override
        public LipsObject evaluate(Frame frame, Environment environment) throws EvaluationException {
        if (specialForm instanceof Quote quote) {
            return LipsObjectFactory.create(quote.getBody());
        }

        if (specialForm instanceof Lambda lambda) {
            return new Procedure(frame.inner("lambda"), environment.inner(), lambda);
        }

        if (specialForm instanceof Func func) {
            var identifier = func.getIdentifier();
            var procedure = new Procedure(frame.inner(identifier.getName()), environment.inner(), func);
            environment.put(func.getIdentifier(), procedure);

            return LipsNil.instance;
        }

        if (specialForm instanceof SetQ setQ) {
            var symbol = new LipsSymbol(setQ.getSymbol());
            var value = LipsObjectFactory.create(setQ.getValue()).evaluate(frame.inner(symbol.getName()), environment);

            environment.put(symbol.getName(), value);

            return LipsNil.instance;
        }

        if (specialForm instanceof If anIf) {
            var predicate = LipsObjectFactory.create(anIf.getPredicate());
            var condFrame = frame.inner("cond");

            if (predicate.evaluate(condFrame, environment).asBoolean()) {
                var branch = LipsObjectFactory.create(anIf.getThenBranch());
                return branch.evaluate(condFrame.inner("then"), environment);
            }

            var branch = LipsObjectFactory.create(anIf.getElseBranch());
            return branch.evaluate(frame.inner("else"), environment);
        }

        if (specialForm instanceof Cond cond) {
            var branches = cond.getBranches();

            var condFrame = frame.inner("cond");
            for (var branch : branches) {
                var predicate = LipsObjectFactory.create(branch.predicate());
                var body = LipsObjectFactory.create(branch.body());
                var branchFrame = condFrame.inner("branch-predicate");

                if (predicate.evaluate(branchFrame, environment).asBoolean()) {
                    var bodyFrame = branchFrame.inner("branch-body");
                    return body.evaluate(bodyFrame, environment);
                }
            }
        }

        if (specialForm instanceof While whileForm) {
            var predicate = LipsObjectFactory.create(whileForm.getPredicate());
            var body = whileForm.getBody();

            var whileFrame = frame.inner("while");
            while (predicate.evaluate(whileFrame, environment).asBoolean()) {
                for (var expression : body) {
                    if (expression instanceof Break) {
                        return LipsNil.instance;
                    }

                    if (expression instanceof Return ret) {
                        return LipsObjectFactory.create(ret.getValue());
                    }

                    LipsObjectFactory
                            .create(expression)
                            .evaluate(whileFrame.inner("while-body"), environment);
                }
            }

            return LipsNil.instance;
        }

        if (specialForm instanceof Prog prog) {
            var bindings = prog.getBindings();

            var progEnv = environment.inner();
            var progFrame = frame.inner("prog");

            for (var binding : bindings) {
                var name = binding.getName();

                var value = LipsObjectFactory.create(binding.getValue()).evaluate(progFrame.inner(name), environment);

                progEnv.put(name, value);
            }

            LipsObject result = LipsNil.instance;
            for (var element : prog.getBody()) {
                if (element instanceof Return ret) {
                    return LipsObjectFactory.create(ret.getValue());
                }

                result = LipsObjectFactory.create(element).evaluate(progFrame, progEnv);
            }

            return result;
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public String type() {
        return "special";
    }
}
