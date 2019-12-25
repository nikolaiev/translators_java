package com.translator.lexic.syntax.descending.units.statement;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

import java.util.LinkedList;

public class StatementList extends SyntaxUnit {

    public StatementList(){
        this.setName("StatementList");

        this.setExactSyntax(new LinkedList<>(){{
            add(Statement.getInstance());
        }});

        this.setLoopLexeme(Lexeme.of(";"));
        this.setLoopMandatory(false);
    }
}
