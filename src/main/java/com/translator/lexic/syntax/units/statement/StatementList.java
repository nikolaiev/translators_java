package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class StatementList extends SyntaxUnit {

    public StatementList(){
        this.setExactSyntax(new LinkedList<>(){{
            add(new Statement());
        }});

        this.setLoopLexeme(Lexeme.of(";"));
    }
}
