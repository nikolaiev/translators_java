package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning.State8;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.returning.State9;

public class State7 extends AbstractState {

    private static AbstractState INSTANCE;

    static{
        INSTANCE = new State7();
        INSTANCE.init();
    }

    public static AbstractState getInstance(){
        return INSTANCE;
    }

    public void init(){
        this.regex="[!<>=]";
        this.nextStateRegexTrigger = "[=]";
        this.nextState = State8.getInstance();
        this.nextStateFast= State9.getInstance();
    }
}
