package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.List;

public final class If extends SpecialForm {
    private final SExpression predicate;
    private final SExpression thenBranch;
    private final SExpression elseBranch;

    private If(Span span, SExpression condition, SExpression thenBranch, SExpression elseBranch) {
        super(span);

        this.predicate = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public static If parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.size() != 3) {
            throw new SpecialFormArityMismatchException(span, "if", 3, elements.size());
        }

        var iter = elements.iterator();

        return new If(span, iter.next(), iter.next(), iter.next());
    }

    public SExpression getPredicate() {
        return predicate;
    }

    public SExpression getThenBranch() {
        return thenBranch;
    }

    public SExpression getElseBranch() {
        return elseBranch;
    }

    @Override
    public String AST() {
        return "If(%s ? %s : %s)".formatted(predicate.AST(), thenBranch.AST(), elseBranch.AST());
    }
}
