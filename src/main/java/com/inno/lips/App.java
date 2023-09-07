package com.inno.lips;

import com.github.tomaslanger.chalk.Chalk;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print(Chalk.on("> ").bold().magenta());
            var line = reader.readLine();

            try {
                List<Token> tokens = Lexer.tokenize(line);
                var sexpr = Parser.parse(tokens.iterator());
                System.out.println(sexpr);
            } catch (LexingException | ParseException e) {
                System.err.println(e.show(line));
            }
        }
    }
}
