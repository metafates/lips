package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.UnexpectedEOFException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SExpressionFactory {
    public static SExpression create(Iterator<Token> tokens) throws ParseException {
        if (!tokens.hasNext()) {
            throw new UnexpectedEOFException();
        }

        var token = tokens.next();
        return create(token.span(), token, tokens);
    }

    private static SExpression create(Span span, Token current, Iterator<Token> rest) throws ParseException {
        return switch (current.type()) {
            case OPEN_PAREN -> {
                List<SExpression> frame = new ArrayList<>();

                while (rest.hasNext()) {
                    var next = rest.next();
                    if (next.type() == TokenType.CLOSE_PAREN) {
                        yield SequenceFactory.create(span.join(next.span()), frame);
                    }

                    frame.add(create(span, next, rest));
                }

                throw new UnexpectedEOFException();
            }
            case CLOSE_PAREN -> throw new InvalidSyntaxException("Unexpected closing parenthesis");
            default -> AtomFactory.create(current);
        };
    }
}
