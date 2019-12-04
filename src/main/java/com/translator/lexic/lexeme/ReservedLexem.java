package com.translator.lexic.lexeme;

import java.util.LinkedHashMap;

import static com.translator.lexic.lexeme.LexemeType.ADD_OP;
import static com.translator.lexic.lexeme.LexemeType.BRACKETS_OP;
import static com.translator.lexic.lexeme.LexemeType.KEYWORD;
import static com.translator.lexic.lexeme.LexemeType.MULT_OP;
import static com.translator.lexic.lexeme.LexemeType.PUNCT;
import static com.translator.lexic.lexeme.LexemeType.REL_OP;
import static com.translator.lexic.lexeme.LexemeType.WHITE;

public class ReservedLexem {

    private ReservedLexem() {
    }

    public static LinkedHashMap<String, LexemeType> RESERVED_LEXEMS = new LinkedHashMap<>() {{
        put("program", KEYWORD);
        put("var", KEYWORD);

        put("int", KEYWORD);
        put("float", KEYWORD);
        put("bool", KEYWORD);
        put("string", KEYWORD);

        put("repeat", KEYWORD);
        put("until", KEYWORD);

        put("if", KEYWORD);
        put("then", KEYWORD);
        put("fi", KEYWORD);

        put("go", KEYWORD);
        put("out", KEYWORD);

        put("=", KEYWORD);

        put("+", ADD_OP);
        put("-", ADD_OP);
        put("*", MULT_OP);
        put("/", MULT_OP);
        put("%", MULT_OP);
        put("^", MULT_OP);

        put("<", REL_OP);
        put("<=", REL_OP);
        put("==", REL_OP);
        put(">=", REL_OP);
        put("!=", REL_OP);
        put(">", REL_OP);

        put("||", REL_OP);
        put("|", REL_OP);
        put("&&", REL_OP);
        put("&", REL_OP);

        put("(", BRACKETS_OP);
        put(")", BRACKETS_OP);
        put("{", BRACKETS_OP);
        put("}", BRACKETS_OP);

        put(",", PUNCT);
        put(":", PUNCT);
        put(";", PUNCT);

        put(" ", WHITE);
        put("\n", WHITE);
        put("\t", WHITE);
        put("\r", WHITE);
    }};
}
