package com.inno.lips;

import com.inno.lips.cli.CLI;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        AnsiConsole.systemInstall();

        CLI.main(args);
    }
}
