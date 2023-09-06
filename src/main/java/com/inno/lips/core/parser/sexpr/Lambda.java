package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Lambda extends SpecialForm {
    private final List<Parameter> parameters;
    private final SExpression body;

    private Lambda(Span span, List<Parameter> parameters, SExpression body) {
        super(span);

        this.parameters = parameters;
        this.body = body;
    }

    public static Lambda parse(Span span, List<SExpression> args) throws ParseException {
        if (args.size() != 2) {
            throw new SpecialFormArityMismatchException(span, "lambda", 2, args.size());
        }

        var parameters = parseParameters(args.get(0));
        var body = args.get(1);

        return new Lambda(span, parameters, body);
    }

    private static List<Parameter> parseParameters(SExpression sExpression) throws ParseException {
        if (!(sExpression instanceof Sequence sequence)) {
            throw new InvalidSyntaxException(sExpression.span(), "lambda accepts list as parameters");
        }

        Set<String> parametersNames = new HashSet<>();
        List<Parameter> parameters = new ArrayList<>();

        for (SExpression element : sequence.getElements()) {
            var parameter = Parameter.parse(element);

            if (parametersNames.contains(parameter.getName())) {
                // TODO: better message
                throw new ParseException(parameter.span(), "duplicated param names");
            }

            parametersNames.add(parameter.getName());
            parameters.add(parameter);
        }

        return parameters;
    }

    @Override
    public String toString() {
        List<String> strings = parameters.stream().map(String::valueOf).toList();
        return "Lambda(%s -> %s)".formatted(String.join(", ", strings), body);
    }
}
