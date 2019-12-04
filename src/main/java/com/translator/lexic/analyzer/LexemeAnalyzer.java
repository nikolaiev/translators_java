package com.translator.lexic.analyzer;

import com.translator.lexic.exception.UnexpectedLexemException;
import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;
import com.translator.lexic.util.LexemValidator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static com.translator.lexic.lexeme.LexemeType.BOOL;
import static com.translator.lexic.lexeme.LexemeType.IDENTIFIER;
import static com.translator.lexic.lexeme.LexemeType.NUMBER;
import static com.translator.lexic.lexeme.LexemeType.STRING;
import static com.translator.lexic.lexeme.ReservedLexem.RESERVED_LEXEMS;
import static com.translator.lexic.util.RegexHolder.IDENTIFIER_REGEX;
import static com.translator.lexic.util.RegexHolder.STRING_REGEX;
import static com.translator.lexic.util.RegexHolder.UNSIGNED_NUMBER_REGEX;

@Data
@Slf4j
public class LexemeAnalyzer {

    public LexemeAnalyzer(String programCode) {
        this.programCode = programCode + " ";
    }

    private final String programCode; //variable to store program code that should be processed

    private String lexemBufferValue = ""; //variable to store temporary parts of program code, before loopLexeme is founded
    private int charIndex = 0; //current position of character that are being read by Analyzer
    private int lineIndex = 1; //current position of line that are being read by Analyzer
    private LinkedList<Lexeme> resultProgramCodeLexemes = new LinkedList<>(); //list of result lexems
    private LinkedHashSet<Lexeme> resultProgramIdentifierLexemes = new LinkedHashSet<>(); //list of result lexems
    private LinkedHashSet<Lexeme> resultProgramLiteralLexemes = new LinkedHashSet<>(); //list of result lexems

    public void analyzeCode() {
        try {
            log.info("Analyzing code \n {} \n", programCode);
            while (charIndex < programCode.length()) {
                Lexeme lexeme = getNextLexemIfPossible();
                if (lexeme != null) {

                    LexemValidator.verify(lexeme);

                    addLexemToResultList(lexeme);

                    LexemeType lexemeType = lexeme.getType();
                    if (lexemeType.equals(IDENTIFIER)) {
                        addLexemToResultIdentifierSet(lexeme);
                    } else if (lexemeType.equals(NUMBER)
                        || lexemeType.equals(BOOL)
                        || lexemeType.equals(STRING)) {
                        addLexemToResultLiteralSet(lexeme);
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored) {
        } catch (UnexpectedLexemException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
            log.error("charIndex {}, buffer {}", charIndex, lexemBufferValue);
        }
    }

    public int getIdentifierIndex(Lexeme lexeme) {
        return new ArrayList<>(resultProgramIdentifierLexemes).indexOf(lexeme);
    }

    public int getLiteralIndex(Lexeme lexeme) {
        return new ArrayList<>(resultProgramLiteralLexemes).indexOf(lexeme);
    }

    private Lexeme getNextLexemIfPossible() {
        appendLexemBufferValueWithNextSymbol();

        if (checkIfKeywordStartsWith()) { //check if buffer matches any reserved keyword
            //since we can have REL_OP like "==" we need to perform this while to prevent having two "=""=" instead of one "=="
            //for keywords like program this loop is useless, since neither "p" nor "pr" is not a reserved loopLexeme (only "program")
            while (appendLexemBufferValueWithNextSymbol() && checkIfKeywordStartsWith()) ;
            //check if buffer is not identifier that starts with part of of whole reserved keyword
            if (!checkMatchesIdentifier()) {
                String possiblyReservedKeyword = getBufferAndClearAndDecreaseCharIdex();
                if (checkIsReservedKeyword(possiblyReservedKeyword)) {
                    //possiblyReservedKeyword is actually a reserved keyword
                    if (possiblyReservedKeyword.equals("\n")) {
                        lineIndex++;
                    }
                    LexemeType lexemeType = RESERVED_LEXEMS.get(possiblyReservedKeyword);
                    return getNewLexem(possiblyReservedKeyword, lexemeType);
                } else {
                    //possiblyReservedKeyword is actually not reserved keyword
                    lexemBufferValue = possiblyReservedKeyword;
                }
            }
        }

        if (checkMatchesIdentifier()) { //check if buffer matches identifier or bool
            while (appendLexemBufferValueWithNextSymbol() && checkMatchesIdentifier()) ;
            String lexemValue = getBufferAndClearAndDecreaseCharIdex();
            //since "true" or "false" can be considered as identifier - check with to set proper type
            LexemeType lexemeType = getTypeIdentifierOrBool(lexemValue);
            return getNewLexem(lexemValue, lexemeType);

        }

        if (checkMatchesUnsignedNumber()) { //check if buffer matches number
            while (appendLexemBufferValueWithNextSymbol() && checkMatchesUnsignedNumber()) ;
            String unsignedNumber = getBufferAndClearAndDecreaseCharIdex();
            return getNewLexem(unsignedNumber, NUMBER);
        }

        if (checkMatchesString()) { //check if buffer matches string
            while (appendLexemBufferValueWithNextSymbol() && checkMatchesString()) ;
            String stringLexem = getBufferAndClearAndDecreaseCharIdex();
            return getNewLexem(stringLexem, STRING);
        }

        return null;
    }

    private Lexeme getNewLexem(String reservedKeyword, LexemeType lexemeType) {
        return Lexeme.of(reservedKeyword, lexemeType, lineIndex);
    }

    private String getBufferAndClearAndDecreaseCharIdex() {
        int lexemBufferValueLength = lexemBufferValue.length();
        String identifier = lexemBufferValue.substring(0, lexemBufferValueLength - 1);
        lexemBufferValue = "";
        charIndex = charIndex - 1;
        return identifier;
    }

    private boolean checkMatchesString() {
        return lexemBufferValue.matches(STRING_REGEX);
    }

    private boolean checkMatchesUnsignedNumber() {
        return lexemBufferValue.matches(UNSIGNED_NUMBER_REGEX);
    }

    private boolean checkMatchesIdentifier() {
        return lexemBufferValue.matches(IDENTIFIER_REGEX);
    }

    private boolean checkIfKeywordStartsWith() {
        return RESERVED_LEXEMS.keySet().stream()
            .anyMatch(keyword -> keyword.startsWith(lexemBufferValue));
    }

    private boolean checkIsReservedKeyword(String str) {
        return RESERVED_LEXEMS.containsKey(str);
    }

    private boolean appendLexemBufferValueWithNextSymbol() {
        lexemBufferValue = lexemBufferValue + programCode.toCharArray()[charIndex];

        if (charIndex < programCode.length()) {
            charIndex = charIndex + 1;
            return true;
        }
        return false;
    }

    private LexemeType getTypeIdentifierOrBool(String lexemValue) {
        return "true".equals(lexemValue) || "false".equals(lexemValue)
            ? BOOL
            : IDENTIFIER;
    }

    private void addLexemToResultList(Lexeme lexeme) {
        resultProgramCodeLexemes.add(lexeme);
    }

    private void addLexemToResultIdentifierSet(Lexeme lexeme) {
        resultProgramIdentifierLexemes.add(lexeme);
    }

    private void addLexemToResultLiteralSet(Lexeme lexeme) {
        resultProgramLiteralLexemes.add(lexeme);
    }
}
