package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.lexeme.LexemeType;
import com.translator.lexic.syntax.units.Identifier;
import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.expression.Expression;

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
