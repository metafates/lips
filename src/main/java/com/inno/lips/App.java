package com.inno.lips;

import com.inno.lips.core.evaluator.Builtin;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.ast.Element;

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

        var scope = new Builtin();
        while (true) {
            try {
                List<Token> tokens = Lexer.tokenize(reader.readLine());

                for (var token : tokens) {
                    System.out.println(token);
                }

                Element element = Parser.parse(tokens);

                System.out.println(element);
//
//                var list = (com.inno.lips.core.parser.ast.List) element;
//                var items = list.getArguments();
//
//                for (var item : items) {
//                    var result = Evaluator.evaluate(item, scope);
//                    System.out.println(result);
//                }
//
            } catch (LexingException e) {
                System.err.println(e.pretty());
            } catch (ParseException e) {
                System.out.println(e.getMessage());
//            } catch (RuntimeException e) {
//                System.out.printf(e.getMessage());
            } catch (IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }
}
