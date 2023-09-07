package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.sexpr.SExpression;
import com.inno.lips.core.parser.sexpr.SExpressionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {
    public static List<SExpression> parse(Iterator<Token> tokens) throws ParseException {
        if (!tokens.hasNext()) {
            throw new UnexpectedEOFException(Span.zero());
        }

        List<SExpression> sExpressions = new ArrayList<>();

        while (tokens.hasNext()) {
            var sExpression = SExpressionFactory.create(tokens);
            sExpressions.add(sExpression);
        }

        return sExpressions;
    }
}
