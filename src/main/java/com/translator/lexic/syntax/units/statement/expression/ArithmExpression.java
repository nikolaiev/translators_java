package com.translator.lexic.syntax.units.statement.expression;

import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import lombok.Data;

import java.util.LinkedList;

//since ArithmExpression contains itself we can get StackOverFlow exception. That is why we need SINGLETON
@Data
public class ArithmExpression extends SyntaxUnit {

    private static ArithmExpression INSTANCE = new ArithmExpression();
    static {
        INSTANCE.init();
    }

    public static ArithmExpression getInstance(){
        return INSTANCE;
    }

    private void init() {
        this.setName("ArithmeticExpression");
        this.setSyntaxOptions(new LinkedList<>() {{
            add(new SyntaxUnit() {{ //ArithmExpression {’+’,'-'} Term
                this.setExactSyntax(new LinkedList<>() {{
                    add(Term.getInstance());
                    add(new SyntaxUnit() {{//{'+','-'}
                        this.setSyntaxOptions(new LinkedList<>() {{
                            add(new ReservedKeyword("+"));
                            add(new ReservedKeyword("-"));
                        }});
                    }});
                    add(ArithmExpression.getInstance());
                }});
            }});
            add(new SyntaxUnit() {{ //-Term
                this.setExactSyntax(new LinkedList<>() {{
                    add(new ReservedKeyword("-"));
                    add(Term.getInstance());
                }});
            }});
            add(Term.getInstance());
        }});
    }

}
