package com.translator.lexic.syntax.units.declaration;

import com.translator.lexic.syntax.units.ReservedKeyword;
import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;

public class TypeKeyword extends SyntaxUnit {


    public TypeKeyword(){
        this.setSyntaxOptions(new LinkedList<>(){{
            add(new ReservedKeyword("int"));
            add(new ReservedKeyword("float"));
            add(new ReservedKeyword("bool"));
            add(new ReservedKeyword("string"));
        }});
    }

}
