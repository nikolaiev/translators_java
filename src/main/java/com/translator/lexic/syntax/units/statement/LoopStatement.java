package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.condition.Condition;

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
            add(new ReservedKeyword("("));
            add(Condition.getInstance());
            add(new ReservedKeyword(")"));
        }});
    }

    @Override
    public String toString(){
        return "LoopStatement";
    }

}
