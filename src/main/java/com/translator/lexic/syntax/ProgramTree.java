package com.translator.lexic.syntax;

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

public class ProgramTree extends SyntaxUnit {
    public ProgramTree() {
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
    }
}
