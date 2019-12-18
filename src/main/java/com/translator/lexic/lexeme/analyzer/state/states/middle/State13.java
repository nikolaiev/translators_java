package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning.State14;

public class State13 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State13();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.regex="[\\|]";
        this.nextState= State14.getInstance();
    }
}
