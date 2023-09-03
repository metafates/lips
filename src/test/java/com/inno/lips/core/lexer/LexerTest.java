package com.inno.lips.core.lexer;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexerTest {
    @Test
    public void shouldTokenizeLiterals() throws LexingException {
        final String input = "true false 1 2.03 \"a string\"";

        var tokens = Lexer.tokenize(input);

        assertEquals(5, tokens.size());

        assertEquals(TokenType.BOOLEAN_LITERAL, tokens.get(0).getType());
        assertEquals("true", tokens.get(0).getSource());

        assertEquals(TokenType.BOOLEAN_LITERAL, tokens.get(1).getType());
        assertEquals("false", tokens.get(1).getSource());

        assertEquals(TokenType.NUMBER_LITERAL, tokens.get(2).getType());
        assertEquals("1", tokens.get(2).getSource());

        assertEquals(TokenType.NUMBER_LITERAL, tokens.get(3).getType());
        assertEquals("2.03", tokens.get(3).getSource());

        assertEquals(TokenType.STRING_LITERAL, tokens.get(4).getType());
        assertEquals("a string", StringUtils.strip(tokens.get(4).getSource(), "\""));
    }
}