package com.translator.lexic.syntax.descending.units.statement;

import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;
import com.translator.lexic.syntax.descending.units.declaration.IdentifiersList;

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
