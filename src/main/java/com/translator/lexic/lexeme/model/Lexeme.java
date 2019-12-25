package com.translator.lexic.lexeme.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Data
public class Lexeme {

    private final String value;
    private final LexemeType type;
    @EqualsAndHashCode.Exclude
    private int lineIndex;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private int code = -1;

    public Lexeme(String value, LexemeType type) {
        this.value = value;
        this.type=type;
    }

    public Lexeme(String value, LexemeType type, int lineIndex) {
        this(value, type);
        this.lineIndex=lineIndex;
    }

    public int getCode() {
        if (code != -1) {
            return code;
        }

        switch (type) {
            case NUMBER: {
                code = 50;
                break;
            }
            case BOOL: {
                code = 51;
                break;
            }
            case STRING: {
                code = 52;
                break;
            }
            case IDENTIFIER: {
                code = 53;
                break;
            }
            default: {
                Set<String> keySet = ReservedLexem.RESERVED_LEXEMES.keySet();
                List keysList = Arrays.asList(keySet.toArray());
                code = keysList.indexOf(value);
            }
        }
        return code;
    }

    public static Lexeme of(String value, LexemeType type, int lineIndex) {
        return new Lexeme(value, type, lineIndex);
    }

    public static Lexeme of(String value, LexemeType type) {
        return new Lexeme(value, type);
    }

    public static Lexeme of(String value) {
        return new Lexeme(value, getLexemType(value));
    }

    public static Lexeme of(LexemeType type) {
        return new Lexeme(null, type);
    }

    private static LexemeType getLexemType(String lexemStr) {
        if(!ReservedLexem.RESERVED_LEXEMES.containsKey(lexemStr)){
            throw new RuntimeException("Nuo such reserved Lexeme "+ lexemStr);
        }
        return ReservedLexem.RESERVED_LEXEMES.get(lexemStr);
    }
}
