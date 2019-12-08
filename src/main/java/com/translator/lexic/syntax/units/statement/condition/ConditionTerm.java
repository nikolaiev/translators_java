package com.translator.lexic.syntax.units.statement.condition;

import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import lombok.Data;

import java.util.LinkedList;

//since Term contains itself we can get StackOverFlow exception. That is why we need SINGLETON
@Data
public class ConditionTerm extends SyntaxUnit {

    private static ConditionTerm INSTANCE = new ConditionTerm();
    static {
        INSTANCE.init();
    }

    public static ConditionTerm getInstance(){
        return INSTANCE;
    }

    private ConditionTerm(){}

    private void init() {
        this.setName("ConditionTerm");

        this.setSyntaxOptions(new LinkedList<>() {{
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>() {{
                    add(ConditionFactor.getInstance());
                    add(new ReservedKeyword("&&"));
                    add(ConditionTerm.getInstance());
                }});
            }});
            add(ConditionFactor.getInstance());
        }});
    }
}
