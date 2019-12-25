package com.translator.lexic.syntax.descending.units.declaration;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

import java.util.LinkedList;

public class IdentifiersList extends SyntaxUnit {

    public IdentifiersList() {
        this.setExactSyntax(new LinkedList<>(){{
            add(new Identifier());
        }});
        this.setLoopLexeme(Lexeme.of(","));
    }
}
