package com.inno.lips.core.parser.element;

import java.util.List;

public sealed class SpecialForm extends ListNode permits While, Cond, Lambda, Set, Break, Return, Quote, Prog, Func {
    public SpecialForm(List<Element> frame) {
        super(frame);
    }
}
