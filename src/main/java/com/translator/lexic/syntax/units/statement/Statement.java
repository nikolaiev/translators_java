package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.expression.Term;

import java.util.LinkedList;

public class Statement extends SyntaxUnit {

    private static Statement INSTANCE = new Statement();
    static {
        INSTANCE.init();
    }

    public static Statement getInstance(){
        return INSTANCE;
    }

    private Statement(){}

    public void init (){
        this.setName("Statement");

        this.setSyntaxOptions(new LinkedList<>(){{
            add(new AssignStatement());
            add(new InputStatement());
            add(new OutputStatement());
            add(new LoopStatement());
        }});
    }
}
