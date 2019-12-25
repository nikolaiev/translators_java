package com.translator.lexic.syntax.descending.units.declaration;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

import java.util.LinkedList;

public class DeclarationList extends SyntaxUnit {

    public DeclarationList(){
        this.setExactSyntax(new LinkedList<>(){{
            add(new Declaration());
        }});
        this.setLoopLexeme(Lexeme.of(";"));
        this.setLoopMandatory(false);
    }
}
