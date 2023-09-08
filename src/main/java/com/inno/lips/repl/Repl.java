package com.inno.lips.repl;

import com.github.tomaslanger.chalk.Chalk;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Evaluator;
import com.inno.lips.core.evaluator.Scope;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.sexpr.SExpression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Repl {
    private final Scope scope;
    private final BufferedReader input;
    private final int inputNumber;

    public Repl() {
        this.inputNumber = 0;
        this.scope = new Scope();
        this.input = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() throws IOException {
        while (true) {
            runExpression();
            System.out.println();
        }
    }

    private String prompt() {
        return Chalk.on("> ").bold().magenta().toString();
    }

    private void runExpression() throws IOException {
        System.out.print(prompt());
        var line = input.readLine();

        if (line.isBlank()) {
            return;
        }
        try {
            List<Token> tokens = Lexer.tokenize(line);
            var sexpr = Parser.parse(tokens.iterator());

            for (SExpression sExpression : sexpr) {
//                System.out.print("AST: ");
//                System.out.println(sExpression.AST());
//
//                System.out.print("REPR: ");
//                System.out.println(sExpression);

                var res = Evaluator.evaluate(scope, sExpression);
//                System.out.print("EVAL: ");
                System.out.println(Chalk.on(res.toString()).green());
            }
        } catch (LexingException | ParseException e) {
            System.err.print(e.show(line));
        } catch (EvaluationException e) {
            System.err.println(e.getMessage());
        }
    }
}
