package com.inno.lips.core.parser;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.sexpr.SExpression;
import com.inno.lips.core.parser.sexpr.SExpressionFactory;

import java.util.Iterator;

public class Parser {
    public static SExpression parse(Iterator<Token> tokens) throws ParseException {
        if (!tokens.hasNext()) {
            throw new UnexpectedEOFException(Span.zero());
        }

        SExpression root = SExpressionFactory.create(tokens);
        while (tokens.hasNext()) {
            root = root.join(SExpressionFactory.create(tokens));
        }

        return root;
    }
}
