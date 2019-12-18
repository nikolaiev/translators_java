package com.translator.lexic.lexeme.analyzer.state.states.error;

import com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer;
import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;

import static java.lang.String.format;

public class State19Error extends AbstractState {

    private static AbstractState INSTANCE;

    static {
        INSTANCE = new State19Error();
        INSTANCE.init();
    }

    public static AbstractState getInstance() {
        return INSTANCE;
    }

    public void init() {
    }

    @Override
    public void process(String buffer) {
        throw new IllegalStateException(format("Illegal sequence found %s at line %s", buffer, LexemeAnalyzer.currentLine));
    }
}
