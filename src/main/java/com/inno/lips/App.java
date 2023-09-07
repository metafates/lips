package com.inno.lips;

import com.inno.lips.repl.Repl;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        var repl = new Repl();

        repl.run();
    }
}
