package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning.State12;

public class State11 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State11();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.regex="[&]";
        this.nextState = State12.getInstance();
    }

}
