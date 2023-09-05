package com.inno.lips.core.parser;

import com.inno.lips.core.lexer.LexingException;
import com.inno.lips.core.parser.element.Element;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParserTest {
    @Test
    public void mustParseValidExpressions() throws LexingException, ParseException {
        List<TestCase> cases = new ArrayList<>();

        // TODO: this is very fragile, comparison should done be better
        cases.add(new TestCase("(1 2 3)", "List(List(Number(1.000000), Number(2.000000), Number(3.000000)))"));
        cases.add(new TestCase("(+ 1)", "List(List(Symbol(+), Number(1.000000)))"));
        cases.add(new TestCase("(here) (and here)", "List(List(Symbol(here)), List(Symbol(and), Symbol(here)))"));
        cases.add(new TestCase("(comment) ; blah blah", "List(List(Symbol(comment)))"));
        cases.add(new TestCase("()", "List(List())"));
        cases.add(new TestCase("(set a-value \"a string\")", "List(Set(Symbol(a-value), String(a string)))"));
        cases.add(new TestCase("(lambda (a) (+ a a))", "List(Lambda([Symbol(a)], List(Symbol(+), Symbol(a), Symbol(a))))"));
        cases.add(new TestCase("((lambda (x) (x)) a)", "List(List(Lambda([Symbol(x)], List(Symbol(x))), Symbol(a)))"));

        for (var test : cases) {
            Element parsed = Parser.parse(test.source);
            Assert.assertEquals(parsed.toString(), test.expected);
        }
    }

    private record TestCase(String source, String expected) {
    }
}