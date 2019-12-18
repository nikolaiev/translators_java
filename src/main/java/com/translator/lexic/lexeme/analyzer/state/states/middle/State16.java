package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning.State17;

public class State16 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State16();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.isCyclic=true;
        this.regex="[^']*";
        this.nextState = State17.getInstance();
    }
}
