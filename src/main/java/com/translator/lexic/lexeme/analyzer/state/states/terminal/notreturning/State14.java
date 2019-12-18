package com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;

public class State14 extends AbstractState {

    private static AbstractState INSTANCE;

    static {
        INSTANCE = new State14();
        INSTANCE.init();
    }

    public static AbstractState getInstance() {
        return INSTANCE;
    }

    public void init() {
        this.isTerminalNotReturned = true;
    }
}
