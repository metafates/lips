package com.inno.lips.core.parser.element;

import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;

import java.util.List;

public final class Break extends SpecialForm {
    private Break(List<Element> frame) {
        super(frame);
    }

    public static Break parse(List<Element> frame) throws ParseException {
        if (frame.size() != 1) {
            throw new InvalidSyntaxException("break expects 1 argument");
        }

        return new Break(frame);
    }

    @Override
    public String toString() {
        return "Break";
    }
}
