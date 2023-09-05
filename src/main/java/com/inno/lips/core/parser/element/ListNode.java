package com.inno.lips.core.parser.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public sealed class ListNode extends Element permits SpecialForm {
    private final List<Element> elements;

    public ListNode() {
        this.elements = new ArrayList<>();
    }

    public ListNode(List<Element> elements) {
        this.elements = elements;
    }

    public ListNode(Element[] elements) {
        this.elements = Arrays.stream(elements).toList();
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        List<String> asStrings = elements.stream().map(String::valueOf).toList();
        return "List(%s)".formatted(String.join(", ", asStrings));
    }
}
