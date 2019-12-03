package com.translator.lexic.util;

import com.translator.lexic.exception.UnexpectedLexemException;
import com.translator.lexic.lexem.Lexem;
import com.translator.lexic.lexem.LexemType;

import static java.lang.String.format;

public class LexemVerifier {

    private LexemVerifier(){
        //utility class
    }

    public static void verify(Lexem lexem){
        if(lexem.getType().equals(LexemType.IDENTIFIER)){
            if(lexem.getValue().equals("#")){
                throw new UnexpectedLexemException(format("Lexem %s at line %d of type %s is invalid lexem",
                    lexem.getValue(),
                    lexem.getLineIndex(),
                    lexem.getType()));
            }
        }
    }
}
