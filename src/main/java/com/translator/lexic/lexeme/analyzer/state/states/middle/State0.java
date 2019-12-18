package com.translator.lexic.lexeme.analyzer.state.states.middle;

import com.translator.lexic.lexeme.analyzer.state.states.AbstractState;
import com.translator.lexic.lexeme.analyzer.state.states.error.State19Error;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning.State10;
import com.translator.lexic.lexeme.analyzer.state.states.terminal.notreturning.State15;

public class State0 extends AbstractState {

    private static AbstractState INSTANCE;

    static {
        INSTANCE = new State0();
        INSTANCE.init();
    }

    public static AbstractState getInstance() {
        return INSTANCE;
    }

    public void init() {
    }

    @Override
    public void process(String buffer) {
        if (buffer.matches("[a-zA-Z]")) {
            State1.getInstance().process(buffer);
        } else if (buffer.matches("[0-9]")) {
            State3.getInstance().process(buffer);
        } else if (buffer.matches("[!<>=]")) {
            State7.getInstance().process(buffer);
        } else if (buffer.matches("[\\n]")) {
            State10.getInstance().process(buffer);
        } else if (buffer.matches("&")) {
            State11.getInstance().process(buffer);
        } else if (buffer.matches("\\|")) {
            State13.getInstance().process(buffer);
        } else if (buffer.matches("[%+-^*/(){},;:\\r\\s\\t]")) {
            State15.getInstance().process(buffer);
        }else if (buffer.matches("[']")) {
            State16.getInstance().process(buffer);
        } else{
            State19Error.getInstance().process(buffer);
        }
    }
}
