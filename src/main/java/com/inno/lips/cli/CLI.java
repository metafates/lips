package com.inno.lips.cli;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.inno.lips.core.lexer.Lexer;
import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.lexer.Token;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.Parser;
import com.inno.lips.core.parser.sexpr.SExpression;
import com.inno.lips.interpreter.Interpreter;
import com.inno.lips.repl.Repl;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

public class CLI {
    public static void main(String[] args) throws IOException {
        var arguments = new Arguments();
        var commander = JCommander
                .newBuilder()
                .addObject(arguments)
                .build();

        try {
            commander.parse(args);
        } catch (ParameterException e) {
            System.err.println(ansi().fg(RED).a(e.getMessage()).reset());
            return;
        }

        // Print help.
        if (arguments.help) {
            commander.usage();
            return;
        }

        // Print tokens.
        if (arguments.tokenize) {
            if (arguments.file == null) {
                System.err.println(ansi().fg(RED).a("file is not provided").reset());
            } else {
                String src = Files.readString(arguments.file.toPath());
                CLI.printTokensFromSource(src);
            }
            return;
        }

        // Print AST.
        if (arguments.ast) {
            if (arguments.file == null) {
                System.err.println(ansi().fg(RED).a("file is not provided").reset());
            } else {
                String src = Files.readString(arguments.file.toPath());
                CLI.printASTFromSource(src);
            }
            return;
        }

        // Otherwise, run the interpreter.
        if (arguments.file != null) {
            String src = Files.readString(arguments.file.toPath());
            Interpreter.interpret(src);
        } else {
            new Repl().loop();
        }
    }

    private static void printTokensFromSource(String src) {
        try {
            List<Token> tokens = new Lexer(src).tokenizeAll();
            for (Token token : tokens) {
                String spanStart = String.format("%05d", token.span().getStart());
                String spanEnd = String.format("%05d", token.span().getEnd());
                String typePadded = String.format("%-18s", token.type());
                String tokenSrc = token.source()
                        .replace("\n", "⏎")
                        .replace("\t", "→")
                        .replace(" ", "·");

                System.out.println(
                        ansi().fg(YELLOW).a(spanStart + ".." + spanEnd).reset()
                                + " "
                                + ansi().fg(BLUE).a(typePadded).reset()
                                + " "
                                + ansi().fg(WHITE).a(tokenSrc).reset()
                );
            }
        } catch (LexingException e) {
            System.out.println("Lexing error:");
            System.err.print(e.getMessage());
        }
    }

    private static void printASTFromSource(String src) {
        try {
            Iterator<Token> tokens = Lexer.tokenize(src).iterator();

            while (tokens.hasNext()) {
                for (SExpression sExpression : Parser.parse(tokens)) {
                    System.out.println(sExpression.AST());
                }
            }
        } catch (LexingException | ParseException e) {
            System.out.println();
            System.err.print(e.show(src));
        }
    }
}
