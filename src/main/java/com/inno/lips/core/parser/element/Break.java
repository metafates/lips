package com.inno.lips.core.parser.element;

import java.util.List;

public final class Break extends SpecialForm {
    private Break(List<Element> frame) {
        super(frame);
    }

    public static Break parse(List<Element> frame) {
        return null;
    }

    @Override
    public String toString() {
        return "Break";
    }
}
