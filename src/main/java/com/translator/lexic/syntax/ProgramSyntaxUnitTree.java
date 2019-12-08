package com.translator.lexic.syntax;

import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.syntax.units.Identifier;
import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.declaration.DeclarationList;
import com.translator.lexic.syntax.units.statement.Statement;
import com.translator.lexic.syntax.units.statement.StatementList;
import com.translator.lexic.syntax.units.statement.expression.ArithmExpression;
import com.translator.lexic.syntax.units.statement.expression.Factor;
import com.translator.lexic.syntax.units.statement.expression.Term;

import java.util.LinkedList;

//Model of syntax
public class ProgramTree {

    public static SyntaxErrorHolder errorHolder = new SyntaxErrorHolder();

    private static SyntaxUnit programSyntaxUnitTree = new SyntaxUnit() {{
        this.setName("ProgramTree");
        this.setExactSyntax(new LinkedList<>() {{
            add(new ReservedKeyword("program"));
            add(new Identifier());
            add(new ReservedKeyword("var"));
            add(new DeclarationList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("{"));
            add(new StatementList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("}"));
        }});
    }};

    public static void validateSyntax(LinkedList<Lexeme> lexemes) {
        errorHolder = new SyntaxErrorHolder(); //clea up shared error holder
        programSyntaxUnitTree.validateSyntax(lexemes);
    }

    private static class SyntaxErrorHolder {
        public String message;
        public String expected;
        public String found;
    }
}
