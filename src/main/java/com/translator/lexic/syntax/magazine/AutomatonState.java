package com.translator.lexic.syntax.magazine;

import com.translator.lexic.lexeme.model.Lexeme;
import lombok.Data;

@Data
public class AutomatonState {

    private boolean exitAutomatonFlag; //state to exit from automaton
    private int number;
    private int nextNumber;
    private Lexeme lexeme;
    private int stack = -1;

    AutomatonState(int thisStateNumber, int nextStateNumber, Lexeme lexeme){
        this.number = thisStateNumber;
        this.nextNumber = nextStateNumber;
        this.lexeme = lexeme;
    }

    AutomatonState(int thisStateNumber, int nextStateNumber,Lexeme lexeme, int stack) {
        this.number = thisStateNumber;
        this.nextNumber = nextStateNumber;
        this.lexeme = lexeme;
        this.stack = stack;
    }

    AutomatonState(int thisStateNumber, int nextStateNumber) {
        this.number = thisStateNumber;
        this.nextNumber = nextStateNumber;
        this.exitAutomatonFlag =true;

    }
}
