package com.translator.lexic.syntax.units.statement.condition;

import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.expression.Factor;
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
        this.setName("Term");

        this.setSyntaxOptions(new LinkedList<>() {{
            add(new SyntaxUnit() {{
                this.setExactSyntax(new LinkedList<>() {{
                    add(Factor.getInstance());
                    add(new SyntaxUnit() {{  // {*,/,^}
                        this.setSyntaxOptions(new LinkedList<>() {{
                            add(new ReservedKeyword("*"));
                            add(new ReservedKeyword("/"));
                            add(new ReservedKeyword("^"));
                        }});
                    }});
                    add(Condition.getInstance());
                }});
            }});
            add(Factor.getInstance());
        }});
    }
}
