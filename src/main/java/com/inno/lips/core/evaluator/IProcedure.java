package com.inno.lips.core.evaluator;

import java.util.List;

@FunctionalInterface
public interface IProcedure {
    LipsObject apply(Frame frame, List<LipsObject> arguments) throws EvaluationException;
}
