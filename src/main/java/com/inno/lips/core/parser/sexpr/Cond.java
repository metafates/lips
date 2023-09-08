package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class Cond extends SpecialForm {
    private final SExpression condition;
    private final SExpression thenBranch;
    private final SExpression elseBranch;

    private Cond(Span span, SExpression condition, SExpression thenBranch, SExpression elseBranch) {
        super(span);

        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public static Cond parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.size() != 3) {
            throw new SpecialFormArityMismatchException(span, "cond", 3, elements.size());
        }

        var iter = elements.iterator();

        return new Cond(span, iter.next(), iter.next(), iter.next());
    }

    public SExpression getCondition() {
        return condition;
    }

    public SExpression getThenBranch() {
        return thenBranch;
    }

    public SExpression getElseBranch() {
        return elseBranch;
    }

    @Override
    public String AST() {
        return "Cond(%s ? %s : %s)".formatted(condition.AST(), thenBranch.AST(), elseBranch.AST());
    }
}
