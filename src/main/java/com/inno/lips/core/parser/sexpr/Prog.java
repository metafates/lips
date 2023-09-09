package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.common.Spannable;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Prog extends SpecialForm {
    private final List<SExpression> body;
    private final Bindings bindings;

    private Prog(Span span, Bindings bindings, List<SExpression> body) {
        super(span);

        this.bindings = bindings;
        this.body = body;
    }

    public static Prog parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.size() < 2) {
            throw new SpecialFormArityMismatchException(span, "prog", 2, elements.size());
        }

        var bindings = Bindings.parse(elements.get(0));
        var body = elements.subList(1, elements.size());

        return new Prog(span, bindings, body);
    }

    public List<Binding> getBindings() {
        return bindings.map.values().stream().toList();
    }

    public List<SExpression> getBody() {
        return body;
    }

    @Override
    public String AST() {
        List<String> strings = body.stream().map(Node::AST).toList();

        return "Prog(%s -> %s)".formatted(bindings.AST(), String.join(", ", strings));
    }

    public static class Binding extends Spannable implements Node {
        private final String name;
        private final SExpression value;

        private Binding(Span span, String name, SExpression value) {
            super(span);

            this.name = name;
            this.value = value;
        }

        public static Binding parse(SExpression sExpression) throws ParseException {
            var span = sExpression.span();
            if (!(sExpression instanceof Sequence sequence) || sequence.getElements().size() != 2) {
                throw new InvalidSyntaxException(span, "format (name value) is expected");
            }

            var elements = sequence.getElements();

            if (!(elements.get(0) instanceof Symbol symbol)) {
                throw new InvalidSyntaxException(elements.get(0).span(), "symbol is expected");
            }

            return new Binding(span, symbol.getName(), elements.get(1));
        }

        public String getName() {
            return name;
        }

        public SExpression getValue() {
            return value;
        }

        @Override
        public String AST() {
            return "Binding(%s, %s)".formatted(name, value.AST());
        }
    }

    public static class Bindings extends Spannable implements Node {
        private final Map<String, Binding> map;

        private Bindings(Span span) {
            super(span);
            this.map = new HashMap<>();
        }

        public static Bindings parse(SExpression sExpression) throws ParseException {
            var span = sExpression.span();
            if (!(sExpression instanceof Sequence sequence)) {
                throw new InvalidSyntaxException(span, "sequence expected");
            }

            var bindings = new Bindings(span);

            for (var element : sequence.getElements()) {
                var binding = Binding.parse(element);

                if (bindings.map.containsKey(binding.name)) {
                    throw new InvalidSyntaxException(binding.span(), "duplicate name");
                }

                bindings.map.put(binding.name, binding);
            }

            return bindings;
        }

        @Override
        public String AST() {
            List<String> strings = map.values().stream().map(Node::AST).toList();

            return "Bindings(%s)".formatted(String.join(", ", strings));
        }
    }
}
