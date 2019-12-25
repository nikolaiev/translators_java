package com.translator.lexic.syntax.descending.units.declaration;

import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

public class Identifier extends SyntaxUnit {

    public Identifier() {
        super(LexemeType.IDENTIFIER);
        this.setName("Identifier");
    }
}
