package com.translator.lexic.syntax.units.statement.expression;

import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class Expression extends SyntaxUnit {

    public Expression(){
        this.setName("Expression");

        this.setSyntaxOptions(new LinkedList<>(){{
            add(ArithmExpression.getInstance());
            add(new BoolExpression());
        }});
    }
}
