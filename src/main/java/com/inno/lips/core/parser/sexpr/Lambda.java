package com.inno.lips.core.parser.sexpr;

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

    private Lambda(List<SExpression> elements, List<Parameter> parameters, SExpression body) {
        super(elements);

        this.parameters = parameters;
        this.body = body;
    }

    public static Lambda parse(Symbol keyword, List<SExpression> args) throws ParseException {
        if (args.size() != 2) {
            throw new SpecialFormArityMismatchException("lambda", 2, args.size());
        }

        var parameters = parseParameters(args.get(0));
        var body = args.get(1);

        List<SExpression> elements = new ArrayList<>();
        elements.add(keyword);
        elements.addAll(args);

        return new Lambda(elements, parameters, body);
    }

    private static List<Parameter> parseParameters(SExpression sExpression) throws ParseException {
        if (!(sExpression instanceof Sequence sequence)) {
            throw new InvalidSyntaxException("lambda accepts list as parameters");
        }

        Set<String> parametersNames = new HashSet<>();
        List<Parameter> parameters = new ArrayList<>();

        for (SExpression element : sequence.getElements()) {
            var parameter = Parameter.parse(element);

            if (parametersNames.contains(parameter.name())) {
                // TODO: better message
                throw new ParseException("duplicated param names");
            }

            parametersNames.add(parameter.name());
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
