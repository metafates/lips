package com.inno.lips.repl;

import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.io.OutputStream;

import static org.fusesource.jansi.Ansi.ansi;

public class LogoWriter {
    private static final String logo = ansi().a("""
            ▄▄▌  ▪   ▄▄▄·.▄▄ ·\s
            ██•  ██ ▐█ ▄█▐█ ▀.\s
            ██▪  ▐█· ██▀·▄▀▀▀█▄
            ▐█▌▐▌▐█▌▐█▪·•▐█▄▪▐█
            .▀▀▀ ▀▀▀.▀    ▀▀▀▀\s
            """).fg(Ansi.Color.YELLOW).toString();

    public static void write(OutputStream stream) throws IOException {
        stream.write(logo.getBytes());
    }
}
