package com.inno.lips.core.parser;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.element.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Parser {
    public static Element parse(String input) throws ParseException, LexingException {
        return parse(Lexer.tokenize(input));
    }

    public static Element parse(List<Token> tokens) throws ParseException {
        boolean stackUpdated = false;
        Deque<List<Element>> stack = new ArrayDeque<>();
        List<Element> frame = new ArrayList<>();

        for (var token : tokens) {
            switch (token.type()) {
                case OPEN_PAREN -> {
                    stackUpdated = true;
                    stack.push(frame);
                    frame = new ArrayList<>();
                }
                case CLOSE_PAREN -> {
                    if (stack.isEmpty()) {
                        throw new ParseException("Unexpected closing parenthesis");
                    }

                    var previousFrame = stack.pop();
                    previousFrame.add(ElementFactory.create(frame));
                    frame = previousFrame;
                }
                default -> {
                    var element = switch (token.type()) {
                        case IDENTIFIER -> new Symbol(token);
                        case STRING_LITERAL -> StringLiteral.from(token);
                        case BOOLEAN_LITERAL -> BooleanLiteral.from(token);
                        case NUMBER_LITERAL -> NumberLiteral.from(token);
                        default -> new Atom(token);
                    };

                    frame.add(element);
                }
            }
        }

        if (!stack.isEmpty()) {
            throw new UnexpectedEOFException();
        }

        if (!stackUpdated) {
            throw new TopLevelExpressionException();
        }

        // wrap whole tree as a list
        return new ListNode(frame);
    }
}
