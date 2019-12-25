package com.translator.lexic.syntax.magazine;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;

import java.util.ArrayList;
import java.util.List;

class AutomatonStateConfiguration {

    static List<AutomatonState> AUTOMATON_STATES;

    static {
        AUTOMATON_STATES = new ArrayList<>();

        AUTOMATON_STATES.add(new AutomatonState(1, 2, Lexeme.of("program")));
        AUTOMATON_STATES.add(new AutomatonState(2, 3, Lexeme.of(LexemeType.IDENTIFIER)));
        AUTOMATON_STATES.add(new AutomatonState(3, 4, Lexeme.of("var")));
        AUTOMATON_STATES.add(new AutomatonState(4, 5, Lexeme.of(LexemeType.IDENTIFIER)));

        AUTOMATON_STATES.add(new AutomatonState(5, 4, Lexeme.of(",")));
        AUTOMATON_STATES.add(new AutomatonState(5, 6, Lexeme.of(":")));

        AUTOMATON_STATES.add(new AutomatonState(6, 7, Lexeme.of("bool")));
        AUTOMATON_STATES.add(new AutomatonState(6, 7, Lexeme.of("int")));
        AUTOMATON_STATES.add(new AutomatonState(6, 7, Lexeme.of("string")));
        AUTOMATON_STATES.add(new AutomatonState(6, 7, Lexeme.of("float")));

        AUTOMATON_STATES.add(new AutomatonState(7, 8, Lexeme.of(";")));
        AUTOMATON_STATES.add(new AutomatonState(8, 5, Lexeme.of(LexemeType.IDENTIFIER)));

        //підавтомат оператор - кладемо в стек 9
        AUTOMATON_STATES.add(new AutomatonState(8, 51, Lexeme.of("{"), 9));
        //підавтомат оператор - кладемо в стек 9
        AUTOMATON_STATES.add(new AutomatonState(9, 51, Lexeme.of(";"), 9));
        //закінчення програми
        AUTOMATON_STATES.add(new AutomatonState(9, -1, Lexeme.of("}")));

        //OPERATOR AUTOMATOR підавтомат оператор
        AUTOMATON_STATES.add(new AutomatonState(51, 90, Lexeme.of("if")));
        AUTOMATON_STATES.add(new AutomatonState(51, 80, Lexeme.of("repeat")));
        AUTOMATON_STATES.add(new AutomatonState(51, 54, Lexeme.of("in")));
        AUTOMATON_STATES.add(new AutomatonState(51, 60, Lexeme.of("out")));
        AUTOMATON_STATES.add(new AutomatonState(51, 70, Lexeme.of(LexemeType.IDENTIFIER)));
        AUTOMATON_STATES.add(new AutomatonState(51, -2)); //exits if no match found
        //in(a,b,..,c)
        AUTOMATON_STATES.add(new AutomatonState(54, 55, Lexeme.of("(")));
        AUTOMATON_STATES.add(new AutomatonState(55, 56, Lexeme.of(LexemeType.IDENTIFIER)));
        AUTOMATON_STATES.add(new AutomatonState(56, 55, Lexeme.of(",")));
        AUTOMATON_STATES.add(new AutomatonState(56, -2, Lexeme.of(")"))); //exits if no match found
        //out(a,2,..,'asd')
        AUTOMATON_STATES.add(new AutomatonState(60, 61, Lexeme.of("(")));
        AUTOMATON_STATES.add(new AutomatonState(61, 62, Lexeme.of(LexemeType.IDENTIFIER)));
        AUTOMATON_STATES.add(new AutomatonState(61, 62, Lexeme.of(LexemeType.BOOL)));
        AUTOMATON_STATES.add(new AutomatonState(61, 62, Lexeme.of(LexemeType.STRING)));
        AUTOMATON_STATES.add(new AutomatonState(61, 62, Lexeme.of(LexemeType.NUMBER)));
        AUTOMATON_STATES.add(new AutomatonState(62, 61, Lexeme.of(",")));
        AUTOMATON_STATES.add(new AutomatonState(62, -2, Lexeme.of(")")));
        //a=<arithmetical expression>
        AUTOMATON_STATES.add(new AutomatonState(70, 71, Lexeme.of("=")));
        AUTOMATON_STATES.add(new AutomatonState(71, 72, Lexeme.of("-")));
        AUTOMATON_STATES.add(new AutomatonState(71, 74, Lexeme.of(LexemeType.IDENTIFIER)));
        AUTOMATON_STATES.add(new AutomatonState(71, 74, Lexeme.of(LexemeType.NUMBER)));
        //71-71 рекурсивний вхід в підавтомат з записом стеку
        AUTOMATON_STATES.add(new AutomatonState(71, 71, Lexeme.of("("), 73));
        AUTOMATON_STATES.add(new AutomatonState(72, 74, Lexeme.of(LexemeType.IDENTIFIER)));
        AUTOMATON_STATES.add(new AutomatonState(72, 74, Lexeme.of(LexemeType.NUMBER)));
        AUTOMATON_STATES.add(new AutomatonState(72, 71, Lexeme.of("("), 73));
        AUTOMATON_STATES.add(new AutomatonState(73, 74, Lexeme.of(")")));
        AUTOMATON_STATES.add(new AutomatonState(74, 72, Lexeme.of("*")));
        AUTOMATON_STATES.add(new AutomatonState(74, 72, Lexeme.of("/")));
        AUTOMATON_STATES.add(new AutomatonState(74, 72, Lexeme.of("%")));
        AUTOMATON_STATES.add(new AutomatonState(74, 72, Lexeme.of("^")));
        AUTOMATON_STATES.add(new AutomatonState(74, 72, Lexeme.of("+")));
        AUTOMATON_STATES.add(new AutomatonState(74, 72, Lexeme.of("-")));
        AUTOMATON_STATES.add(new AutomatonState(74, -2));  //exit from automaton

        //repeat statement
        //use operators automator
        AUTOMATON_STATES.add(new AutomatonState(80, 51, Lexeme.of("{"), 82));
        AUTOMATON_STATES.add(new AutomatonState(82, 51, Lexeme.of(";"), 82));
        AUTOMATON_STATES.add(new AutomatonState(82, 83, Lexeme.of("}")));

        AUTOMATON_STATES.add(new AutomatonState(83, 84, Lexeme.of("until")));
        //use condition automator
        AUTOMATON_STATES.add(new AutomatonState(84, 201, Lexeme.of("("), 85));
        AUTOMATON_STATES.add(new AutomatonState(85, 86, Lexeme.of(")")));
        AUTOMATON_STATES.add(new AutomatonState(86, -2)); //exit from automaton

        //if statement
        //use operators automator
        AUTOMATON_STATES.add(new AutomatonState(90, 201, Lexeme.of("("), 91));
        AUTOMATON_STATES.add(new AutomatonState(91, 92, Lexeme.of(")")));
        AUTOMATON_STATES.add(new AutomatonState(92, 93, Lexeme.of("then")));
        //use operators automator
        AUTOMATON_STATES.add(new AutomatonState(93, 51, Lexeme.of("{"), 94));
        AUTOMATON_STATES.add(new AutomatonState(94, 51, Lexeme.of(";"), 94));
        AUTOMATON_STATES.add(new AutomatonState(94, 95, Lexeme.of("}")));
        AUTOMATON_STATES.add(new AutomatonState(95, -2, Lexeme.of("fi")));

        //condition statement
        //recursion
        AUTOMATON_STATES.add(new AutomatonState(201, 201, Lexeme.of("("), 204));
        AUTOMATON_STATES.add(new AutomatonState(201, 201, Lexeme.of("!")));
        AUTOMATON_STATES.add(new AutomatonState(201, 203, Lexeme.of(LexemeType.BOOL)));
        //expression automaton
        AUTOMATON_STATES.add(new AutomatonState(201, 72, Lexeme.of("-"), 202));
        AUTOMATON_STATES.add(new AutomatonState(201, 74, Lexeme.of(LexemeType.IDENTIFIER), 202));
        AUTOMATON_STATES.add(new AutomatonState(201, 74, Lexeme.of(LexemeType.NUMBER), 202));

        AUTOMATON_STATES.add(new AutomatonState(202, 71, Lexeme.of(">"), 203));
        AUTOMATON_STATES.add(new AutomatonState(202, 71, Lexeme.of(">="), 203));
        AUTOMATON_STATES.add(new AutomatonState(202, 71, Lexeme.of("!="), 203));
        AUTOMATON_STATES.add(new AutomatonState(202, 71, Lexeme.of("<="), 203));
        AUTOMATON_STATES.add(new AutomatonState(202, 71, Lexeme.of("=="), 203));

        AUTOMATON_STATES.add(new AutomatonState(203, 201, Lexeme.of("&&")));
        AUTOMATON_STATES.add(new AutomatonState(203, 201, Lexeme.of("||")));
        AUTOMATON_STATES.add(new AutomatonState(203, -2));
        AUTOMATON_STATES.add(new AutomatonState(204, 203, Lexeme.of(")")));
    }

    private AutomatonStateConfiguration() {
        //utility class
    }
}
