package com.translator.lexic.syntax.units;

import com.translator.lexic.lexeme.Lexeme;

public class ReservedKeyword extends SyntaxUnit {

    public ReservedKeyword(String reservedKeyword) {
        super(Lexeme.of(reservedKeyword));
        this.setName("ReservedKeyword [" + reservedKeyword+"]");
    }
}
