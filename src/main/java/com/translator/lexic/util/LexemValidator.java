package com.translator.lexic.util;

import com.translator.lexic.exception.UnexpectedLexemException;
import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;

import static java.lang.String.format;

public class LexemValidator {

    private LexemValidator(){
        //utility class
    }

    public static void verify(Lexeme lexeme){
        if(lexeme.getType().equals(LexemeType.IDENTIFIER)){
            if(lexeme.getValue().equals("#")){
                throw new UnexpectedLexemException(format("Lexeme %s at line %d of type %s is invalid lexeme",
                    lexeme.getValue(),
                    lexeme.getLineIndex(),
                    lexeme.getType()));
            }
        }
    }
}
