package com.inno.lips.core.inspector;

import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.sexpr.SExpression;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Inspector {
    public static void AST(File file) throws IOException {
        try (Scanner reader = new Scanner(file)) {
            var builder = new StringBuilder();
            while (reader.hasNextLine()) {
                builder.append(reader.nextLine());
            }

            AST(builder.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AST(String string) {
        try {
            Iterator<Token> tokens = Lexer.tokenize(string).iterator();

            while (tokens.hasNext()) {
                for (SExpression sExpression : Parser.parse(tokens)) {
                    System.out.println(sExpression.AST());
                }
            }
        } catch (LexingException | ParseException e) {
            System.out.println();
            System.err.print(e.show(string));
        }
    }
}
