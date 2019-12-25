package com.translator.lexic.syntax.descending.units.statement.expression;

import com.translator.lexic.syntax.descending.units.SyntaxUnit;

import static com.translator.lexic.lexeme.model.LexemeType.NUMBER;

public class NumberConstant extends SyntaxUnit {

    public NumberConstant(){
        this.setName("NumberConstant");
        this.setLexemeType(NUMBER);
    }
}
