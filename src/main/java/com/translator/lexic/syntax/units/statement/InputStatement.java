package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.declaration.IdentifiersList;

import java.util.LinkedList;

public class InputStatement extends SyntaxUnit {

    public InputStatement() {
        this.setName("InputStatement");

        this.setExactSyntax(new LinkedList<>() {{
            add(new ReservedKeyword("in"));
            add(new ReservedKeyword("("));
            add(new IdentifiersList());
            add(new ReservedKeyword(")"));
        }});
    }
}
