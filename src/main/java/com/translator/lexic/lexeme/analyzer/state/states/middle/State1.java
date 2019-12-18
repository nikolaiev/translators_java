package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.returning.State2;

public class State1 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State1();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.regex="[a-zA-Z0-9]";
        this.isCyclic = true;
        this.nextState = State2.getInstance();
    }
}
