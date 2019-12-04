package com.translator.lexic.syntax.units.declaration;

import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.syntax.units.Identifier;
import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class IdentifiersList extends SyntaxUnit {

    public IdentifiersList() {
        this.setExactSyntax(new LinkedList<>(){{
            add(new Identifier());
        }});
        this.setLoopLexeme(Lexeme.of(","));
    }
}
