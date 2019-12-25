package com.translator.lexic.syntax.descending.units.statement;

import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;
import com.translator.lexic.syntax.descending.units.statement.condition.Condition;

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
