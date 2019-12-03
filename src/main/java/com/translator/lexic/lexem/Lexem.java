package com.translator.lexic.lexem;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Data
public class Lexem {

    private final String value;
    private final LexemType type;
    @EqualsAndHashCode.Exclude
    private final int lineIndex;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private int lexemCode = -1;

    public int getLexemCode() {
        if (lexemCode != -1) {
            return lexemCode;
        }

        switch (type) {
            case NUMBER: {
                lexemCode = 50;
                break;
            }
            case BOOL: {
                lexemCode = 51;
                break;
            }
            case STRING: {
                lexemCode = 52;
                break;
            }
            case IDENTIFIER: {
                lexemCode = 53;
                break;
            }
            default: {
                Set<String> keySet = ReservedLexem.RESERVED_LEXEMS.keySet();
                List keysList = Arrays.asList(keySet.toArray());
                lexemCode = keysList.indexOf(value);
            }
        }
        return lexemCode;
    }

    public static Lexem of(String value, LexemType type, int lineIndex) {
        return new Lexem(value, type, lineIndex);
    }
}
