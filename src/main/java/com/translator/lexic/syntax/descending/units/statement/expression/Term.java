package com.translator.lexic.syntax.descending.units.statement.expression;

import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.SyntaxUnit;
import lombok.Data;

import java.util.LinkedList;

//since Term contains itself we can get StackOverFlow exception. That is why we need SINGLETON
@Data
public class Term extends SyntaxUnit {

    private static Term INSTANCE = new Term();
    static {
        INSTANCE.init();
    }

    public static Term getInstance(){
        return INSTANCE;
    }

    private Term(){}

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
                    add(Term.getInstance());
                }});
            }});
            add(Factor.getInstance());
        }});
    }
}
