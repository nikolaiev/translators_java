package com.translator.lexic.syntax.units.statement.expression;

import com.translator.lexic.syntax.units.Identifier;
import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class Factor extends SyntaxUnit {
    private static Factor INSTANCE = new Factor();
    static {
        INSTANCE.init();
    }

    public static Factor getInstance(){
        return INSTANCE;
    }

    private Factor(){}

    public void init() {
        this.setName("Factor");

        this.setSyntaxOptions(new LinkedList<>(){{
            add(new Identifier());
            add(new NumberConstant());
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>(){{
                    add(new ReservedKeyword("("));
                    add(ArithmExpression.getInstance());
                    add(new ReservedKeyword(")"));
                }});
            }});
        }});
    }
}
