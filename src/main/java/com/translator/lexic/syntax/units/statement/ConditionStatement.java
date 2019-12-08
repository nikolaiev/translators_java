package com.translator.lexic.syntax.units.statement;

import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;
import com.translator.lexic.syntax.units.statement.condition.Condition;

import java.util.LinkedList;

public class ConditionStatement extends SyntaxUnit {

    public ConditionStatement(){
        this.setName("ConditionStatement");

        this.setExactSyntax(new LinkedList<>(){{
            add(new ReservedKeyword("if"));
            add(new ReservedKeyword("("));
            add(Condition.getInstance());
            add(new ReservedKeyword(")"));
            add(new ReservedKeyword("then"));
            add(new SyntaxUnit() {{
                this.setSyntaxOptions(new LinkedList<>(){{
                    add(new SyntaxUnit() {{
                        this.setExactSyntax(new LinkedList<>(){{
                            add(Statement.getInstance());
                            add(new ReservedKeyword(";"));
                        }});
                    }});
                    add(new SyntaxUnit() {{
                        this.setExactSyntax(new LinkedList<>(){{
                            add(new ReservedKeyword("{"));
                            add(new StatementList());
                            add(new ReservedKeyword(";"));
                            add(new ReservedKeyword("}"));
                        }});
                    }});
                }});
            }});
            add(new ReservedKeyword("fi"));
        }});
    }

    @Override
    public String toString(){
        return "LoopStatement";
    }

}
