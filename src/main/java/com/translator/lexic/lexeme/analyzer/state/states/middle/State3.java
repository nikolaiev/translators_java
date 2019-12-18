package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.returning.State6;

public class State3 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State3();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.regex="[0-9]";
        this.nextStateRegexTrigger="\\.";
        this.isCyclic=true;
        this.nextState=State4.getInstance();
        this.nextStateFast= State6.getInstance();
    }
}
