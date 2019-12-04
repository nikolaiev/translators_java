package com.translator.lexic.syntax.units.declaration;

import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.syntax.units.SyntaxUnit;

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
