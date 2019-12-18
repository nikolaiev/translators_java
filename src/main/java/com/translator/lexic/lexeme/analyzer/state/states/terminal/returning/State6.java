package com.translator.lexic.lexeme.analyzer.state.states.terminal.returning;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;

public class State6 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State6();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.isTerminal= true;
    }
}
