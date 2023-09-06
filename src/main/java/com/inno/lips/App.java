package com.inno.lips;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                List<Token> tokens = Lexer.tokenize(reader.readLine());
                for (var token : tokens) {
                    System.out.println(token);
                }

                var sexpr = Parser.parse(tokens.iterator());
                System.out.println(sexpr);
//                if (sexpr instanceof Sequence sequence) {
//                    for (var element : sequence.getElements()) {
//                        System.out.println(element.span());
//                    }
//                }
//
//                System.out.println(sexpr.span());
            } catch (LexingException e) {
                System.err.println(e.pretty());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }
}
