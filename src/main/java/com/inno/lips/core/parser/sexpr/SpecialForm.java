package com.inno.lips.core.parser.sexpr;

import java.util.List;

public abstract sealed class SpecialForm extends Sequence permits Set, Lambda {
    public SpecialForm(List<SExpression> elements) {
        super(elements);
    }
}
