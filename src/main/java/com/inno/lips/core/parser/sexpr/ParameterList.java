package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParameterList implements Node {
    private final Set<String> names;
    private final List<Parameter> parameters;
    private boolean parameterWithDefaultValueAppeared;

    private ParameterList() {
        this.parameters = new ArrayList<>();
        this.names = new HashSet<>();
        this.parameterWithDefaultValueAppeared = false;
    }

    public static ParameterList parse(SExpression sExpression) throws ParseException {
        if (!(sExpression instanceof Sequence sequence)) {
            throw new InvalidSyntaxException(sExpression.span(), "lambda accepts list as parameters");
        }

        if (sequence instanceof SpecialForm) {
            throw new InvalidSyntaxException(sExpression.span(), "special form inside function parameters");
        }

        var parameters = new ParameterList();

        for (var element : sequence.getElements()) {
            var parameter = Parameter.parse(element);
            parameters.add(parameter);
        }

        return parameters;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    private void add(Parameter parameter) throws ParseException {
        if (names.contains(parameter.getName())) {
            // TODO: better message
            throw new ParseException(parameter.span(), "Duplicate parameter");
        }

        if (parameter.hasDefault()) {
            parameterWithDefaultValueAppeared = true;
        } else if (parameterWithDefaultValueAppeared) {
            throw new ParseException(parameter.span(), "Non-default argument follows default argument");
        }

        names.add(parameter.getName());
        parameters.add(parameter);
    }

    @Override
    public String AST() {
        List<String> strings = parameters.stream().map(Parameter::AST).toList();
        String joined = String.join(", ", strings);

        return "Parameters(%s)".formatted(joined);
    }
}
