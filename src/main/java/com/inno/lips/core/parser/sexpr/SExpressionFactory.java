package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.lexer.TokenType;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormWithoutCallingException;
import com.inno.lips.core.parser.UnexpectedEOFException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SExpressionFactory {
    public static SExpression create(Iterator<Token> tokens) throws ParseException {
        if (!tokens.hasNext()) {
            throw new UnexpectedEOFException(new Span(0, 0));
        }

        var token = tokens.next();

        if (token.type().isSpecial()) {
            throw new SpecialFormWithoutCallingException(token.span());
        }

        return create(token.span(), token, tokens);
    }

    private static SExpression create(Span span, Token current, Iterator<Token> rest) throws ParseException {
        return create(span, current, rest, false);
    }

    private static SExpression create(Span span, Token current, Iterator<Token> rest, boolean quoted) throws ParseException {
        return switch (current.type()) {
            case OPEN_PAREN -> {
                List<SExpression> frame = new ArrayList<>();

                // for the error
                Token lastToken = null;
                while (rest.hasNext()) {
                    var next = rest.next();
                    lastToken = next;

                    if (next.type() == TokenType.CLOSE_PAREN) {
                        var sequenceSpan = current.span().join(next);

                        yield SequenceFactory.create(sequenceSpan, frame, quoted);
                    }

                    var created = create(span, next, rest, quoted);
                    if (!quoted && created instanceof Atom atom) {
                        quoted = atom.getType() == TokenType.QUOTE;
                    }

                    frame.add(created);
                }

                if (lastToken == null) {
                    throw new UnexpectedEOFException(current.span());
                }

                throw new UnexpectedEOFException(current.span().join(lastToken));
            }
            case CLOSE_PAREN -> throw new InvalidSyntaxException(current.span(), "Unexpected closing parenthesis");
            default -> {
                Atom atom = AtomFactory.create(current);
                if (atom.getType() != TokenType.QUOTE_TICK) yield atom;

                Symbol quote = new Symbol(current.span(), TokenType.QUOTE, "quote");

                if (!rest.hasNext()) throw new UnexpectedEOFException(quote.span());

                var next = rest.next();
                SExpression toQuote = create(next.span(), next, rest, true);

                List<SExpression> elements = new ArrayList<>();
                elements.add(quote);
                elements.add(toQuote);

                yield SequenceFactory.create(quote.span().join(toQuote), elements, true);
            }
        };
    }
}
