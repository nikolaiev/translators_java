package com.translator.lexic.syntax.descending.units.statement.expression;

import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

public class RelationOperator extends SyntaxUnit {

    public RelationOperator() {
        this.setName("RelationOperator");

        this.setLexemeType(LexemeType.REL_OP);
    }
}
