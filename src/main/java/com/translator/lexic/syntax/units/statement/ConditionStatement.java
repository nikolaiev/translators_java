package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.expression.BoolExpression;

import java.util.LinkedList;

public class ConditionStatement extends SyntaxUnit {

    public ConditionStatement(){
        this.setName("LoopStatement");

        this.setExactSyntax(new LinkedList<>(){{
            add(new ReservedKeyword("repeat"));
            add(new ReservedKeyword("{"));
            add(new StatementList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("}"));
            add(new ReservedKeyword("until"));
            add(BoolExpression.getInstance());
        }});
    }

    @Override
    public String toString(){
        return "LoopStatement";
    }

}
