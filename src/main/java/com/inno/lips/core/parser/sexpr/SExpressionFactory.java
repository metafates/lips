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

        return create(token.span(), token, tokens, false);
    }

    private static SExpression create(Span span, Token current, Iterator<Token> rest, boolean quoted) throws ParseException {
        return switch (current.type()) {
            case OPEN_PAREN -> {
                List<SExpression> frame = new ArrayList<>();

                Token lastToken = null;
                while (rest.hasNext()) {
                    var next = rest.next();
                    lastToken = next;

                    if (next.type() == TokenType.CLOSE_PAREN) {
                        var sequenceSpan = current.span().join(next);

                        if (quoted) {
                            yield new Sequence(sequenceSpan, frame);
                        }

                        yield SequenceFactory.create(sequenceSpan, frame);
                    }

                    frame.add(create(span, next, rest, quoted));
                }

                if (lastToken == null) {
                    throw new UnexpectedEOFException(current.span());
                }

                throw new UnexpectedEOFException(current.span().join(lastToken));
            }
            case CLOSE_PAREN -> throw new InvalidSyntaxException(current.span(), "Unexpected closing parenthesis");
            default -> {
                Atom atom = AtomFactory.create(current);

                yield switch (atom.getType()) {
                    case QUOTE_TICK -> expandQuoteMacro(atom.span(), rest);
                    default -> atom;
                };
            }
        };
    }

    private static Quote expandQuoteMacro(Span span, Iterator<Token> tokens) throws ParseException {
        if (!tokens.hasNext()) {
            // TODO: more verbose message
            throw new UnexpectedEOFException(span);
        }

        var next = tokens.next();
        SExpression toQuote = create(next.span(), next, tokens, true);

        return Quote.parse(span.join(toQuote.span()), toQuote);
    }
}
