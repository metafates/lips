package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.parser.ParseException;
import org.apache.commons.lang.NotImplementedException;

import java.util.List;

public class SpecialFormFactory {
    public static SpecialForm create(Symbol head, List<SExpression> sExpressions) throws ParseException {
        return switch (head.getToken().orElseThrow().type()) {
            case SET -> Set.parse(head, sExpressions);
            default -> throw new NotImplementedException("not implemented");
        };
    }
}
