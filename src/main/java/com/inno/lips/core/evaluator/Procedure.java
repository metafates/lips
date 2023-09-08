package com.inno.lips.core.evaluator;


import com.inno.lips.core.parser.sexpr.Func;
import com.inno.lips.core.parser.sexpr.Lambda;
import com.inno.lips.core.parser.sexpr.Parameter;
import com.inno.lips.core.parser.sexpr.SExpression;

import java.util.List;

public final class Procedure extends LipsObject implements IProcedure {
    private final Scope scope;
    private final IProcedure inner;

    public Procedure(IProcedure inner) {
        this.scope = new Scope();
        this.inner = inner;
    }

    public Procedure(Scope scope, Lambda lambda) {
        this.scope = scope;
        this.inner = fromParamsAndBody(scope, lambda.getParameters(), lambda.getBody());
    }

    public Procedure(Scope scope, Func func) {
        this.scope = scope;
        this.inner = fromParamsAndBody(scope, func.getParameters(), func.getBody());
    }

    private static IProcedure fromParamsAndBody(Scope scope, List<Parameter> parameters, SExpression body) {
        return arguments -> {
            var defaultsArity = (int) parameters.stream().filter(Parameter::hasDefault).count();
            var requiredArity = parameters.size() - defaultsArity;
            var givenArity = arguments.size();

            if (givenArity < requiredArity || givenArity > requiredArity + defaultsArity) {
                // TODO: better message
                throw new EvaluationException("arity mismatch");
            }

            for (int i = 0; i < givenArity; i++) {
                var param = parameters.get(i);
                scope.put(param.getName(), arguments.get(i));
            }

            for (int i = givenArity; i < requiredArity + defaultsArity; i++) {
                var param = parameters.get(i);
                var value = new LipsObject(param.getDefaultValue());
                scope.put(param.getName(), value);
            }

            return Evaluator.evaluate(scope, body);
        };
    }

    public LipsObject apply(List<LipsObject> arguments) throws EvaluationException {
        assert inner != null;
        return inner.apply(arguments);
    }

    @Override
    public String toString() {
        return "<function>";
    }
}
