package com.inno.lips.core.parser;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ast.Atom;
import com.inno.lips.core.parser.ast.Expression;
import com.inno.lips.core.parser.ast.ExpressionFactory;
import com.inno.lips.core.parser.ast.SyntaxObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Parser {
    private final List<Token> tokens;

    private Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public static Expression parse(String input) throws ParseException, LexingException {
        return new Parser(Lexer.tokenize(input)).parse();
    }

    public static Expression parse(List<Token> tokens) throws ParseException {
        return new Parser(tokens).parse();
    }

    private Expression parse() throws ParseException {
        Deque<List<Expression>> stack = new ArrayDeque<>();
        List<Expression> frame = new ArrayList<>();

        for (var token : tokens) {
            switch (token.type()) {
                case OPEN_PAREN -> {
                    stack.push(frame);
                    frame = new ArrayList<>();
                }
                case CLOSE_PAREN -> {
                    if (stack.isEmpty()) {
                        throw new ParseException("Unexpected closing paren");
                    }

                    var previousFrame = stack.pop();
                    previousFrame.add(ExpressionFactory.create(frame));
                    frame = previousFrame;
                }
                default -> frame.add(new Atom(new SyntaxObject(token)));
            }
        }

        if (!stack.isEmpty()) {
            throw new UnexpectedEOFException();
        }

        return ExpressionFactory.create(frame);
    }
}
