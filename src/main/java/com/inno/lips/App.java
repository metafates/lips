package com.inno.lips;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.ast.Expression;

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

                Expression expression = Parser.parse(tokens);

                System.out.println(expression);
            } catch (LexingException e) {
                System.err.println(e.pretty());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
