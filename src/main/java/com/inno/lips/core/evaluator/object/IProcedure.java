package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;

import java.util.List;

@FunctionalInterface
public interface IProcedure {
    LipsObject apply(Frame frame, List<LipsObject> arguments) throws EvaluationException;
}
