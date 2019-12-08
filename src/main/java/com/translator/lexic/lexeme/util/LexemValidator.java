package com.translator.lexic.lexeme.util;

import com.translator.lexic.lexeme.exception.UnexpectedLexemException;
import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;

import static java.lang.String.format;

public class LexemValidator {

    private LexemValidator(){
        //utility class
    }

    public static void verifyLexem(Lexeme lexeme){
        if(lexeme.getType().equals(LexemeType.IDENTIFIER)){
            if(lexeme.getValue().equals("#")){
                throw new UnexpectedLexemException(format("Lexeme %s at line %d of type %s is invalid lexeme",
                    lexeme.getValue(),
                    lexeme.getLineIndex(),
                    lexeme.getType()));
            }
        }
    }

    public static void verifyBuffer(String buffer, int charIndex, int lineIndex){
        if(buffer.length()==2){
            if(buffer.indexOf("!")==0){
                throw new UnexpectedLexemException(format("Code fragment ' %s ' at line %d at character index %s is invalid. Expected ' != '",
                    buffer,
                    lineIndex,
                    charIndex));
            }
        }
    }
}
