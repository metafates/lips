package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.UnexpectedEOFException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SExpressionFactory {
    public static SExpression create(List<Token> tokens) throws ParseException {

        Deque<Token> stack = new ArrayDeque<>(tokens);

        if (stack.isEmpty()) {
            throw new UnexpectedEOFException();
        }

        SExpression root = createOne(stack);
        while (!stack.isEmpty()) {
            root = root.join(createOne(stack));
        }

        return root;
    }

    private static SExpression createOne(Deque<Token> tokens) throws ParseException {
        if (tokens.isEmpty()) {
            throw new UnexpectedEOFException();
        }

        var token = tokens.pop();

        return switch (token.type()) {
            case OPEN_PAREN -> {
                List<SExpression> frame = new ArrayList<>();

                while (true) {
                    if (tokens.peek() == null) {
                        throw new UnexpectedEOFException();
                    }

                    if (tokens.peek().type() == TokenType.CLOSE_PAREN) break;

                    var parsed = createOne(tokens);
                    frame.add(parsed);
                }

                tokens.pop();
                yield SequenceFactory.create(frame);
            }
            case CLOSE_PAREN -> throw new InvalidSyntaxException("Unexpected closing parenthesis");
            default -> AtomFactory.create(token);
        };
    }
}
