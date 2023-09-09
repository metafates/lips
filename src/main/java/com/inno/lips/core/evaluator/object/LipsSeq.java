package com.inno.lips.core.evaluator.object;

import com.inno.lips.core.evaluator.Environment;
import com.inno.lips.core.evaluator.EvaluationException;
import com.inno.lips.core.evaluator.Frame;
import com.inno.lips.core.evaluator.NotCallableException;
import com.inno.lips.core.parser.sexpr.Sequence;

import java.util.ArrayList;
import java.util.List;

public final class LipsSeq extends LipsObject {
    private final List<LipsObject> elements;

    public LipsSeq(List<LipsObject> elements) {
        this.elements = elements;
    }

    public LipsSeq() {
        this.elements = new ArrayList<>();
    }

    public LipsSeq(Sequence sequence) {
        this.elements = new ArrayList<>();

        for (var element : sequence.getElements()) {
            var object = LipsObjectFactory.create(element);

            elements.add(object);
        }
    }

    public List<LipsObject> getElements() {
        return elements;
    }

    @Override
    public boolean asBoolean() {
        return !elements.isEmpty();
    }

    @Override
    public LipsObject evaluate(Frame frame, Environment environment) throws EvaluationException {
        if (elements.isEmpty()) {
            return LipsNil.instance;
        }

        var iter = elements.iterator();
        var head = iter.next().evaluate(frame, environment);

        if (!(head instanceof Procedure procedure)) {
            throw new NotCallableException(frame);
        }

        List<LipsObject> arguments = new ArrayList<>();

        while (iter.hasNext()) {
            var argument = iter.next().evaluate(frame, environment);
            arguments.add(argument);
        }

        return procedure.apply(frame, arguments);
    }

    @Override
    public String type() {
        return "sequence";
    }

    @Override
    public String toString() {
        List<String> strings = elements.stream().map(String::valueOf).toList();
        return "(%s)".formatted(String.join(" ", strings));
    }
}
