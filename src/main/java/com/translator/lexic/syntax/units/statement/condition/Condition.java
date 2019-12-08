package com.translator.lexic.syntax.units.statement.condition;

import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import lombok.Data;

import java.util.LinkedList;

//since Term contains itself we can get StackOverFlow exception. That is why we need SINGLETON
@Data
public class Condition extends SyntaxUnit {

    private static Condition INSTANCE = new Condition();
    static {
        INSTANCE.init();
    }

    public static Condition getInstance(){
        return INSTANCE;
    }

    private Condition(){}

    private void init() {
        this.setName("Condition");

        this.setSyntaxOptions(new LinkedList<>() {{
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>(){{
                    add(new ReservedKeyword("!"));
                    add(ConditionTerm.getInstance());
                }});
            }
            });
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>(){{
                    add(ConditionTerm.getInstance());
                    add(new ReservedKeyword("||"));
                    add(Condition.getInstance());
                }});
            }});
            add(ConditionTerm.getInstance());
        }});
    }
}
