package com.translator.lexic.syntax.descending.units.statement;

import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;
import com.translator.lexic.syntax.descending.units.declaration.IdentifiersList;
import com.translator.lexic.syntax.descending.units.statement.expression.Expression;

import java.util.LinkedList;

public class OutputStatement extends SyntaxUnit {

    public OutputStatement() {
        this.setName("OutputStatement");

        this.setExactSyntax(new LinkedList<>() {{
            add(new ReservedKeyword("out"));
            add(new ReservedKeyword("("));
            add(new SyntaxUnit() {{
                this.setSyntaxOptions(new LinkedList<>(){{
                    add(new IdentifiersList());
                    add(new Expression());
                }});
            }
            });
            add(new ReservedKeyword(")"));
        }});
    }
}
