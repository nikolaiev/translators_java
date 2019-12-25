package com.translator.lexic.syntax.descending.units.statement.condition;

import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;
import com.translator.lexic.syntax.descending.units.statement.expression.BoolExpression;
import lombok.Data;

import java.util.LinkedList;

@Data
public class ConditionFactor extends SyntaxUnit {

    private static ConditionFactor INSTANCE = new ConditionFactor();

    static {
        INSTANCE.init();
    }

    public static ConditionFactor getInstance() {
        return INSTANCE;
    }

    private ConditionFactor() {
    }

    private void init() {
        this.setName("ConditionFactor");

        this.setSyntaxOptions(new LinkedList<>() {{
            add(BoolExpression.getInstance());
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>(){{
                    add(new ReservedKeyword("("));
                    add(Condition.getInstance());
                    add(new ReservedKeyword(")"));
                }});
            }});
            add(Condition.getInstance());
        }});
    }
}
