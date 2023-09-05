package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class Func extends SpecialForm {
    private Func(List<Element> frame) {
        super(frame);
    }

    public static Func parse(List<Element> frame) throws ParseException {
        return null;
    }
}
