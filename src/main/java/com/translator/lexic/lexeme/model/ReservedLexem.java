package com.translator.lexic.lexeme.model;

import java.util.LinkedHashMap;

import static com.translator.lexic.lexeme.model.LexemeType.ADD_OP;
import static com.translator.lexic.lexeme.model.LexemeType.BRACKETS_OP;
import static com.translator.lexic.lexeme.model.LexemeType.KEYWORD;
import static com.translator.lexic.lexeme.model.LexemeType.MULT_OP;
import static com.translator.lexic.lexeme.model.LexemeType.PUNCT;
import static com.translator.lexic.lexeme.model.LexemeType.REL_OP;
import static com.translator.lexic.lexeme.model.LexemeType.WHITE;

public class ReservedLexem {

    private ReservedLexem() {
    }

    public static LinkedHashMap<String, LexemeType> RESERVED_LEXEMES = new LinkedHashMap<>() {{
        put("program", KEYWORD); //0
        put("var", KEYWORD); //1

        put("int", KEYWORD); //2
        put("float", KEYWORD); //3
        put("bool", KEYWORD); //4
        put("string", KEYWORD); //5

        put("repeat", KEYWORD); //6
        put("until", KEYWORD); //7

        put("if", KEYWORD); //8
        put("then", KEYWORD); //9
        put("fi", KEYWORD); //10

        put("in", KEYWORD); //11
        put("out", KEYWORD); //12

        put("=", KEYWORD); //13

        put("+", ADD_OP); //14
        put("-", ADD_OP); //15
        put("*", MULT_OP); //16
        put("/", MULT_OP); //17
        put("%", MULT_OP); //18
        put("^", MULT_OP); //19

        put("<", REL_OP); //20
        put("<=", REL_OP); //21
        put("==", REL_OP); //22
        put(">=", REL_OP); //23
        put("!=", REL_OP); //24
        put("!", REL_OP); //25
        put(">", REL_OP); //26

        put("||", REL_OP); //27
        put("&&", REL_OP); //28

        put("(", BRACKETS_OP); //29
        put(")", BRACKETS_OP); //30
        put("{", BRACKETS_OP); //31
        put("}", BRACKETS_OP); //32

        put(",", PUNCT); //33
        put(":", PUNCT); //34
        put(";", PUNCT); //35

        put(" ", WHITE); //36
        put("\n", WHITE); //37
        put("\t", WHITE); //38
        put("\r", WHITE); //39
    }};
}
