package com.translator.lexic.syntax.units.statement.expression;

import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;
import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

import static com.translator.lexic.lexeme.LexemeType.NUMBER;

public class NumberConstant extends SyntaxUnit {

    public NumberConstant(){
        this.setName("NumberConstant");
        this.setLexemeType(NUMBER);
    }
}
