package com.inno.lips;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                var tokens = Lexer.tokenize(reader.readLine());

                for (var token : tokens) {
                    System.out.println(token);
                }
            } catch (LexingException e) {
                System.err.println(e.pretty());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
