package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;

import java.util.List;

public abstract sealed class SpecialForm extends Sequence permits Break, Logic, Cond, Func, If, Lambda, Prog, Quote, Return, SetQ, While {
    public SpecialForm(Span span) {
        super(span);
    }

    @Override
    public String AST() {
        List<String> strings = getElements().stream().map(String::valueOf).toList();
        return "Special(%s)".formatted(String.join(", ", strings));
    }

    @Override
    public boolean asBoolean() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
