package com.translator.lexic.syntax.units.statement.expression;

import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class BoolExpression extends SyntaxUnit {

    //TODO rewirte
    public BoolExpression(){
        this.setName("BoolExpression");
        this.setExactSyntax(new LinkedList<>(){{
            add(ArithmExpression.getInstance());
            add(new RelationOperator());
            add(ArithmExpression.getInstance());
        }});
    }
}
