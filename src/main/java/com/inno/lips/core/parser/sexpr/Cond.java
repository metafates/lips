package com.inno.lips.core.parser.sexpr;

import com.inno.lips.core.common.Span;
import com.inno.lips.core.parser.InvalidSyntaxException;
import com.inno.lips.core.parser.ParseException;
import com.inno.lips.core.parser.SpecialFormArityMismatchException;

import java.util.ArrayList;
import java.util.List;

public final class Cond extends SpecialForm {
    private final List<Branch> branches;

    private Cond(Span span, List<Branch> branches) {
        super(span);

        this.branches = branches;
    }

    public static Cond parse(Span span, List<SExpression> elements) throws ParseException {
        if (elements.isEmpty()) {
            throw new SpecialFormArityMismatchException(span, "cond", 1, 0);
        }

        List<Branch> branches = new ArrayList<>();

        for (var element : elements) {
            branches.add(Branch.parse(element));
        }

        return new Cond(span, branches);
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public record Branch(SExpression predicate, SExpression body) {
        public static Branch parse(SExpression sExpression) throws ParseException {
            if (!(sExpression instanceof Sequence sequence)) {
                throw new InvalidSyntaxException(sExpression.span(), "sequence expected");
            }

            var elements = sequence.getElements();

            if (elements.size() != 2) {
                throw new SpecialFormArityMismatchException(sequence.span(), "cond-predicate", 2, elements.size());
            }

            return new Branch(elements.get(0), elements.get(1));
        }
    }
}
