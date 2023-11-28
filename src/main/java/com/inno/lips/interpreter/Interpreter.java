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

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class Interpreter {
    private static Frame frame() {
        return new Frame("<file>");
    }

    public static Environment interpret(java.io.File file) throws IOException {
        String src = Files.readString(file.toPath());
        return interpret(src);
    }

    public static Environment interpret(String src) {
        var environment = new Environment();

        try {
            Iterator<Token> tokens = Lexer.tokenize(src).iterator();

            while (tokens.hasNext()) {
                for (SExpression sExpression : Parser.parse(tokens)) {
                    Evaluator.evaluate(frame(), environment, sExpression);
                }
            }
        } catch (LexingException | ParseException e) {
            System.out.println();
            System.err.print(e.show(src));
        } catch (EvaluationException e) {
            System.out.println();
            System.out.print(e.trace());
        }

        return environment;
    }
}
