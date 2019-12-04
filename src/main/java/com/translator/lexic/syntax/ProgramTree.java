package com.translator.lexic.syntax;

import com.translator.lexic.syntax.units.Identifier;
import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.declaration.Declaration;
import com.translator.lexic.syntax.units.declaration.DeclarationList;
import com.translator.lexic.syntax.units.declaration.IdentifiersList;

import java.util.LinkedList;

public class ProgramTree extends SyntaxUnit {

    public ProgramTree() {
        this.setExactSyntax(new LinkedList<SyntaxUnit>() {{
            add(new ReservedKeyword("program"));
            add(new Identifier());
            add(new ReservedKeyword("var"));
            add(new DeclarationList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("{"));
////            add(new StatementList()); //TODO statement list
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("}"));
        }});
    }
}
