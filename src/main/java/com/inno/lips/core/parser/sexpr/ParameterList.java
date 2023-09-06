package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.*;

public class ParameterList extends ArrayList<Parameter> {
    private ParameterList(@NotNull Collection<? extends Parameter> c) {
        super(c);
    }

    public static ParameterList parse(SExpression sExpression) throws ParseException {
        if (!(sExpression instanceof Sequence sequence)) {
            throw new InvalidSyntaxException(sExpression.span(), "lambda accepts list as parameters");
        }

        Set<String> parametersNames = new HashSet<>();
        List<Parameter> parameters = new ArrayList<>();

        boolean paramWithDefaultValueMet = false;

        for (SExpression element : sequence.getElements()) {
            var parameter = Parameter.parse(element);

            if (parametersNames.contains(parameter.getName())) {
                // TODO: better message
                throw new ParseException(parameter.span(), "duplicated param names");
            }

            parametersNames.add(parameter.getName());
            parameters.add(parameter);

            if (parameter.getDefaultValue().isPresent()) {
                paramWithDefaultValueMet = true;
            } else if (paramWithDefaultValueMet) {
                throw new ParseException(parameter.span(), "non-default argument follows default argument");
            }
        }

        return new ParameterList(parameters);
    }
}
