package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.expression.BoolExpression;

import java.util.LinkedList;

public class LoopStatement extends SyntaxUnit {

    public LoopStatement(){
        this.setName("LoopStatement");

        this.setExactSyntax(new LinkedList<>(){{
            add(new ReservedKeyword("repeat"));
            add(new ReservedKeyword("{"));
            add(new StatementList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("}"));
            add(new ReservedKeyword("until"));
            add(new BoolExpression());


        }});
    }

}
