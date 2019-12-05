package com.translator.lexic.syntax.units;

import com.translator.lexic.lexeme.LexemeType;

public class Identifier extends SyntaxUnit {

    public Identifier() {
        super(LexemeType.IDENTIFIER);
        this.setName("Identifier");
    }
}
