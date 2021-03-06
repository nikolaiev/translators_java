package com.translator.lexic.syntax.descending.units.declaration;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

public class ReservedKeyword extends SyntaxUnit {

    public ReservedKeyword(String reservedKeyword) {
        super(Lexeme.of(reservedKeyword));
        this.setName("ReservedKeyword [" + reservedKeyword+"]");
    }
}
