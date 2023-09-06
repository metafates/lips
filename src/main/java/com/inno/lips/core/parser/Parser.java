package com.inno.lips.core.parser;

import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.sexpr.SExpression;
import com.inno.lips.core.parser.sexpr.SExpressionFactory;

import java.util.List;

public class Parser {
    public static SExpression parse(List<Token> tokens) throws ParseException {
        return SExpressionFactory.create(tokens);
    }
}
