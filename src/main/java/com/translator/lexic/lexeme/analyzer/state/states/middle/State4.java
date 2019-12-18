package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.returning.State5;

public class State4 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State4();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.isCyclic=true;
        this.regex="[0-9]";
        this.nextState = State5.getInstance();
    }
}
