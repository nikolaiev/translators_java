package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class Statement extends SyntaxUnit {

    public Statement(){
        this.setSyntaxOptions(new LinkedList<>(){{
            add(new AssignStatement());
            add(new InputStatement());
            add(new OutputStatement());
            add(new LoopStatement());
        }});
    }
}
