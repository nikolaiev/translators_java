package com.translator.lexic.lexeme.util;

import com.translator.lexic.lexeme.exception.UnexpectedLexemException;
import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;

import static java.lang.String.format;

public class LexemValidator {

    private LexemValidator(){
        //utility class
    }

    public static void verifyLexeme(Lexeme lexeme){
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
