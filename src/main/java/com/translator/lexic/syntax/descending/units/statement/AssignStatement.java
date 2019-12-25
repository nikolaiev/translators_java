package com.translator.lexic.syntax.descending.units.statement;

import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.descending.units.declaration.Identifier;
import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;
import com.translator.lexic.syntax.descending.units.statement.expression.Expression;

import java.util.LinkedList;

public class AssignStatement extends SyntaxUnit {

    public AssignStatement() {
        this.setName("AssignStatement");

        this.setExactSyntax(new LinkedList<>() {{
            add(new Identifier());
            add(new ReservedKeyword("="));
            add(new SyntaxUnit() {{
                    this.setSyntaxOptions(new LinkedList<>() {{
                        add(new SyntaxUnit() {{
                            this.setLexemeType(LexemeType.STRING);
                        }});
                        add(new SyntaxUnit() {{
                            this.setLexemeType(LexemeType.BOOL);
                        }});
                        add(new Expression());

                    }});
                }});
        }});
    }
}
