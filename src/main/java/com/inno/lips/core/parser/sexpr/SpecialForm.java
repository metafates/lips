package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;

import java.util.List;

public abstract sealed class SpecialForm extends Sequence permits Func, Lambda, Quote, Return, Set, While {
    public SpecialForm(Span span) {
        super(span);
    }

    @Override
    public String AST() {
        List<String> strings = getElements().stream().map(String::valueOf).toList();
        return "Special(%s)".formatted(String.join(", ", strings));
    }
}
