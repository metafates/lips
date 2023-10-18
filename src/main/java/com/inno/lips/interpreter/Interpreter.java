package com.inno.lips.interpreter;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Evaluator;
import com.inno.lips.core.evaluator.Frame;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.sexpr.SExpression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class Interpreter {
    public static Environment interpret(File file, boolean printAST) {
        try (Scanner reader = new Scanner(file)) {
            var builder = new StringBuilder();
            while (reader.hasNextLine()) {
                builder.append(reader.nextLine());
            }

            return interpret(builder.toString(), printAST);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return new Environment();
    }

    private static Frame frame() {
        return new Frame("<file>");
    }

    public static Environment interpret(String program, boolean printAST) {
        var environment = new Environment();

        try {
            Iterator<Token> tokens = Lexer.tokenize(program).iterator();

            while (tokens.hasNext()) {
                for (SExpression sExpression : Parser.parse(tokens)) {
                    if (printAST) {
                        System.out.println(sExpression.AST());
                    }

                    Evaluator.evaluate(frame(), environment, sExpression);
                }
            }
        } catch (LexingException | ParseException e) {
            System.out.println();
            System.err.print(e.show(program));
        } catch (EvaluationException e) {
            System.out.println();
            System.out.print(e.trace());
        }

        return environment;
    }
}
