package com.inno.lips.core.parser;

import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.sexpr.SExpression;
import com.inno.lips.core.parser.sexpr.SExpressionFactory;

import java.util.Iterator;

public class Parser {
    public static SExpression parse(Iterator<Token> tokens) throws ParseException {
        if (!tokens.hasNext()) {
            throw new UnexpectedEOFException();
        }

        SExpression root = SExpressionFactory.create(tokens);
        while (tokens.hasNext()) {
            root = root.join(SExpressionFactory.create(tokens));
        }

        return root;
    }
}
