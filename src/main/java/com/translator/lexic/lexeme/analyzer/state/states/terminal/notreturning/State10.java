package com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning;

import com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer;
import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;

public class State10 extends AbstractState {

    private static AbstractState INSTANCE;

    static {
        INSTANCE = new State10();
        INSTANCE.init();
    }

    public static AbstractState getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.isTerminalNotReturned = true;
    }

    @Override
    public void process(String buffer){
        LexemeAnalyzer.currentLine = LexemeAnalyzer.currentLine +1;
        super.process(buffer);
    }
}
