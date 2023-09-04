package com.inno.lips.core.parser.ast;

import org.apache.commons.lang.StringUtils;

public class StringLiteral extends Literal<String> {
    public StringLiteral(SyntaxObject syntaxObject) {
        super(getString(syntaxObject), syntaxObject);
    }

    private static String getString(SyntaxObject syntaxObject) {
        String source = syntaxObject.token().source();
        return StringUtils.strip(source, "\"");
    }
}
