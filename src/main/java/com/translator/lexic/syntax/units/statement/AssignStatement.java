package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.Identifier;
import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.expression.Expression;

import java.util.LinkedList;

public class AssignStatement extends SyntaxUnit {

    public AssignStatement() {
        this.setExactSyntax(new LinkedList<>(){{
            add(new Identifier());
            add(new ReservedKeyword("="));
            add(new Expression());
        }});
    }
}
