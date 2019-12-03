package com.translator.lexic.lexem;

import java.util.LinkedHashMap;

import static com.translator.lexic.lexem.LexemType.ADD_OP;
import static com.translator.lexic.lexem.LexemType.BRACKETS_OP;
import static com.translator.lexic.lexem.LexemType.KEYWORD;
import static com.translator.lexic.lexem.LexemType.MULT_OP;
import static com.translator.lexic.lexem.LexemType.PUNCT;
import static com.translator.lexic.lexem.LexemType.REL_OP;
import static com.translator.lexic.lexem.LexemType.WHITE;

public class ReservedLexem {

    private ReservedLexem() {
    }

    public static LinkedHashMap<String, LexemType> RESERVED_LEXEMS = new LinkedHashMap<>() {{
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

        put(" ", WHITE);
        put("\n", WHITE);
        put("\t", WHITE);
    }};
}
