package com.translator.lexic.lexeme.analyzer.state.states.terminal.returning;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;

public class State5 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State5();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.isTerminal= true;
    }
}
