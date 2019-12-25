package com.translator.lexic.syntax.descending.units.declaration;

import com.translator.lexic.syntax.descending.units.SyntaxUnit;

import java.util.LinkedList;

public class Declaration extends SyntaxUnit {

    public Declaration(){
        this.setExactSyntax(new LinkedList<>(){{
            add(new IdentifiersList());
            add(new ReservedKeyword(":"));
            add(new TypeKeyword());
        }});
    }
}
