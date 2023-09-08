package com.inno.lips.core.evaluator;

import java.util.List;

@FunctionalInterface
public interface IProcedure {
    LipsObject apply(List<LipsObject> arguments) throws EvaluationException;
}
