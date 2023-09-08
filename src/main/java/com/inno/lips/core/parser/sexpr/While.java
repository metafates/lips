package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class While extends SpecialForm {
    private final SExpression condition;
    private final List<SExpression> body;

    private While(Span span, SExpression condition, List<SExpression> body) {
        super(span);

        this.condition = condition;
        this.body = body;
    }

    public static While parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.size() < 2) {
            throw new SpecialFormArityMismatchException(span, "while", 2, elements.size());
        }

        var condition = elements.get(0);
        var body = elements.subList(1, elements.size());

        return new While(span, condition, body);
    }

    public SExpression getCondition() {
        return condition;
    }

    public List<SExpression> getBody() {
        return body;
    }

    @Override
    public String AST() {
        List<String> strings = body.stream().map(SExpression::AST).toList();
        return "While(%s -> %s)".formatted(condition.AST(), String.join(", ", strings));
    }
}
