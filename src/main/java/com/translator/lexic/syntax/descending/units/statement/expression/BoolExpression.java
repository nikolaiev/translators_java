package com.translator.lexic.syntax.descending.units.statement.expression;

import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;

import java.util.LinkedList;

//TODO fix bool expression
public class BoolExpression extends SyntaxUnit {

    private static BoolExpression INSTANCE = new BoolExpression();
    static {
        INSTANCE.init();
    }

    public static BoolExpression getInstance(){
        return INSTANCE;
    }

    private BoolExpression(){}

    private void init (){
        this.setName("BoolExpression");
        this.setSyntaxOptions(new LinkedList<>(){{
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>(){{
                    add(ArithmExpression.getInstance());
                    add(new RelationOperator());
                    add(ArithmExpression.getInstance());
                }});
            }});

            add(new SyntaxUnit() {{
                this.setLexemeType(LexemeType.BOOL);
            }});
        }});
    }
}
