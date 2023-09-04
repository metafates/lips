package com.inno.lips.core.lexer;

import junit.framework.AssertionFailedError;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexerTest {
    @Test
    public void shouldNotThrowWhenTokenizingEmptyString() {
        try {
            Lexer.tokenize("");
        } catch (LexingException e) {
            throw new AssertionFailedError();
        }
    }

    @Test
    public void shouldTokenizeLiterals() throws LexingException {
        final String input = "true false 1 2.03 \"a string\"";

        var tokens = Lexer.tokenize(input);

        assertEquals(5, tokens.size());

        assertEquals(TokenType.BOOLEAN_LITERAL, tokens.get(0).type());
        assertEquals("true", tokens.get(0).source());

        assertEquals(TokenType.BOOLEAN_LITERAL, tokens.get(1).type());
        assertEquals("false", tokens.get(1).source());

        assertEquals(TokenType.NUMBER_LITERAL, tokens.get(2).type());
        assertEquals("1", tokens.get(2).source());

        assertEquals(TokenType.NUMBER_LITERAL, tokens.get(3).type());
        assertEquals("2.03", tokens.get(3).source());

        assertEquals(TokenType.STRING_LITERAL, tokens.get(4).type());
        assertEquals("a string", StringUtils.strip(tokens.get(4).source(), "\""));
    }
}