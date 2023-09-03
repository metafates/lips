package com.inno.lips;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final String input = """
                (print "some string" true)
                """;
        try {
            var tokens = Lexer.tokenize(input);

            for (var token : tokens) {
                System.out.println(token);
            }
        } catch (LexingException e) {
            System.out.println(e.pretty(input));
        }
    }
}
