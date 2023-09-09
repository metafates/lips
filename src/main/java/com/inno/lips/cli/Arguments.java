package com.inno.lips.cli;

import com.beust.jcommander.Parameter;

import java.io.File;

class Arguments {
    @Parameter(description = "File", converter = FileConverter.class)
    File file;


    @Parameter(names = {"-h", "--help"}, description = "Show help", help = true)
    boolean help;
}
