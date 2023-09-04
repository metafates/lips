package com.inno.lips.core.parser;

import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ast.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Parser {
    private final List<Token> tokens;

    private Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public static Element parse(List<Token> tokens) throws ParseException {
        return new Parser(tokens).parse();
    }

    private Element parse() throws ParseException {
        Deque<List<Element>> stack = new ArrayDeque<>();
        List<Element> frame = new ArrayList<>();

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
                    previousFrame.add(ElementFactory.create(frame));
                    frame = previousFrame;
                }
                default -> {
                    Atom element = switch (token.type()) {
                        case IDENTIFIER -> new Symbol(new SyntaxObject(token));
                        case STRING_LITERAL -> new StringLiteral(new SyntaxObject(token));
                        case BOOLEAN_LITERAL -> new BooleanLiteral(new SyntaxObject(token));
                        case NUMBER_LITERAL -> new NumberLiteral(new SyntaxObject(token));
                        default -> new Atom(new SyntaxObject(token));
                    };

                    frame.add(element);
                }
            }
        }

        if (!stack.isEmpty()) {
            throw new UnexpectedEOFException();
        }

        return ElementFactory.create(frame);
    }
}
