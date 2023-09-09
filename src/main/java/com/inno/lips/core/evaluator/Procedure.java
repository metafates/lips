package com.inno.lips.core.evaluator;


import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.sexpr.Func;
import com.inno.lips.core.parser.sexpr.Lambda;
import com.inno.lips.core.parser.sexpr.Parameter;
import com.inno.lips.core.parser.sexpr.SExpression;

import java.util.List;

public final class Procedure extends LipsObject implements IProcedure {
    private final Frame frame;
    private final IProcedure inner;

    public Procedure(IProcedure inner) {
        this.inner = inner;
        this.frame = new Frame(Span.zero(), "<builtin>");
    }

    public Procedure(Frame frame, Environment environment, Lambda lambda) {
        this.frame = frame;
        this.inner = fromParamsAndBody(environment, lambda.getParameters(), lambda.getBody());
    }

    public Procedure(Frame frame, Environment environment, Func func) {
        this.frame = frame;
        this.inner = fromParamsAndBody(environment, func.getParameters(), func.getBody());
    }

    private IProcedure fromParamsAndBody(Environment environment, List<Parameter> parameters, SExpression body) {
        return (frame, arguments) -> {
            var defaultsArity = (int) parameters.stream().filter(Parameter::hasDefault).count();
            var requiredArity = parameters.size() - defaultsArity;
            var givenArity = arguments.size();

            if (givenArity < requiredArity || givenArity > requiredArity + defaultsArity) {
                // TODO: better message
                throw new EvaluationException(frame, "arity mismatch");
            }

            for (int i = 0; i < givenArity; i++) {
                var param = parameters.get(i);
                environment.put(param.getName(), arguments.get(i));
            }

            for (int i = givenArity; i < requiredArity + defaultsArity; i++) {
                var param = parameters.get(i);
                var value = new LipsObject(param.getDefaultValue());
                environment.put(param.getName(), value);
            }

            return Evaluator.evaluate(frame, environment, body);
        };
    }

    public LipsObject apply(Frame frame, List<LipsObject> arguments) throws EvaluationException {
        return inner.apply(frame.inner(this.frame.getSpan(), this.frame.getScope()), arguments);
    }

    @Override
    public String toString() {
        return "<function>";
    }
}
