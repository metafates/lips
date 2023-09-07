package com.inno.lips.repl;

import com.github.tomaslanger.chalk.Chalk;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.sexpr.SExpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class Repl {
    private final BufferedReader input;

    public Repl(Reader input) {
        this.input = new BufferedReader(input);
    }

    public Repl() {
        this.input = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        while (true) {
            runLine();
        }
    }

    private void runLine() throws IOException {
        System.out.print(Chalk.on("> ").bold().magenta());
        var line = input.readLine();

        if (line.isBlank()) {
            return;
        }

        try {
            List<Token> tokens = Lexer.tokenize(line);
            var sexpr = Parser.parse(tokens.iterator());

            for (SExpression sExpression : sexpr) {
                System.out.println(sExpression);
            }
        } catch (LexingException | ParseException e) {
            System.err.println(e.show(line));
        }
    }
}
