package com.translator.lexic.syntax.units.statement.expression;

import com.translator.lexic.lexeme.LexemeType;
import com.translator.lexic.syntax.units.SyntaxUnit;

public class RelationOperator extends SyntaxUnit {

    public RelationOperator() {
        this.setName("RelationOperator");

        this.setLexemeType(LexemeType.REL_OP);
    }
}
