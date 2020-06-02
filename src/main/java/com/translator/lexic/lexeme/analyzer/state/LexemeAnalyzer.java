package com.translator.lexic.lexeme.analyzer.state;

import com.translator.lexic.lexeme.analyzer.state.states.middle.State0;
import com.translator.lexic.lexeme.model.Lexeme;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

@Data
@Slf4j
public class LexemeAnalyzer {

    public static String PROGRAM_CODE;

    public static int currentLine = 0;

    public static LinkedList<Lexeme> resultProgramCodeLexemes = new LinkedList<>(); //list of result lexems
    public static LinkedHashSet<Lexeme> resultProgramIdentifierLexemes = new LinkedHashSet<>(); //list of result lexems
    public static LinkedHashSet<Lexeme> resultProgramLiteralLexemes = new LinkedHashSet<>(); //list of result lexems

    public LexemeAnalyzer(String programCode) {
        resultProgramCodeLexemes = new LinkedList<>();
        resultProgramIdentifierLexemes = new LinkedHashSet<>();
        resultProgramLiteralLexemes = new LinkedHashSet<>();
        PROGRAM_CODE = programCode + " ";
    }

    public void analyzeCode() {
        char firstChar = PROGRAM_CODE.charAt(0);
        PROGRAM_CODE = PROGRAM_CODE.substring(1);
        State0.getInstance().process(String.valueOf(firstChar));
    }

    public LinkedList<Lexeme> getResultProgramCodeLexemes() {
        return resultProgramCodeLexemes;
    }

    public LinkedHashSet<Lexeme> getResultProgramIdentifierLexemes() {
        return resultProgramIdentifierLexemes;
    }

    public LinkedHashSet<Lexeme> getResultProgramLiteralLexemes() {
        return resultProgramLiteralLexemes;
    }

    public int getIdentifierIndex(Lexeme lexeme) {
        return new ArrayList<>(resultProgramIdentifierLexemes).indexOf(lexeme);
    }

    public int getLiteralIndex(Lexeme lexeme) {
        return new ArrayList<>(resultProgramLiteralLexemes).indexOf(lexeme);
    }
}
