package com.translator.lexic.analyzer;

import com.translator.lexic.exception.UnexpectedLexemException;
import com.translator.lexic.lexem.Lexem;
import com.translator.lexic.lexem.LexemType;
import com.translator.lexic.util.LexemVerifier;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static com.translator.lexic.lexem.LexemType.BOOL;
import static com.translator.lexic.lexem.LexemType.IDENTIFIER;
import static com.translator.lexic.lexem.LexemType.NUMBER;
import static com.translator.lexic.lexem.LexemType.STRING;
import static com.translator.lexic.lexem.ReservedLexem.RESERVED_LEXEMS;
import static com.translator.lexic.util.RegexHolder.IDENTIFIER_REGEX;
import static com.translator.lexic.util.RegexHolder.STRING_REGEX;
import static com.translator.lexic.util.RegexHolder.UNSIGNED_NUMBER_REGEX;

@Data
@Slf4j
public class LexemAnalyzer {

    public LexemAnalyzer(String programCode) {
        this.programCode = programCode + " ";
    }

    private final String programCode; //variable to store program code that should be processed

    private String lexemBufferValue = ""; //variable to store temporary parts of program code, before lexem is founded
    private int charIndex = 0; //current position of character that are being read by Analyzer
    private int lineIndex = 1; //current position of line that are being read by Analyzer
    private LinkedList<Lexem> resultProgramCodeLexems = new LinkedList<>(); //list of result lexems
    private LinkedHashSet<Lexem> resultProgramIdentifierLexems = new LinkedHashSet<>(); //list of result lexems
    private LinkedHashSet<Lexem> resultProgramLiteralLexems = new LinkedHashSet<>(); //list of result lexems

    public void analyzeCode() {
        try {
            log.info("Analyzing code \n {} \n", programCode);
            while (charIndex < programCode.length()) {
                Lexem lexem = getNextLexemIfPossible();
                if (lexem != null) {

                    LexemVerifier.verify(lexem);

                    addLexemToResultList(lexem);

                    LexemType lexemType = lexem.getType();
                    if (lexemType.equals(IDENTIFIER)) {
                        addLexemToResultIdentifierSet(lexem);
                    } else if (lexemType.equals(NUMBER)
                        || lexemType.equals(BOOL)
                        || lexemType.equals(STRING)) {
                        addLexemToResultLiteralSet(lexem);
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

    public int getIdentifierIndex(Lexem lexem) {
        return new ArrayList<>(resultProgramIdentifierLexems).indexOf(lexem);
    }

    public int getLiteralIndex(Lexem lexem) {
        return new ArrayList<>(resultProgramLiteralLexems).indexOf(lexem);
    }

    private Lexem getNextLexemIfPossible() {
        appendLexemBufferValueWithNextSymbol();

        if (checkIfKeywordStartsWith()) { //check if buffer matches any reserved keyword
            //since we can have REL_OP like "==" we need to perform this while to prevent having two "=""=" instead of one "=="
            //for keywords like program this loop is useless, since neither "p" nor "pr" is not a reserved lexem (only "program")
            while (appendLexemBufferValueWithNextSymbol() && checkIfKeywordStartsWith()) ;
            //check if buffer is not identifier that starts with part of of whole reserved keyword
            if (!checkMatchesIdentifier()) {
                String possiblyReservedKeyword = getBufferAndClearAndDecreaseCharIdex();
                if (checkIsReservedKeyword(possiblyReservedKeyword)) {
                    //possiblyReservedKeyword is actually a reserved keyword
                    if (possiblyReservedKeyword.equals("\n")) {
                        lineIndex++;
                    }
                    LexemType lexemType = RESERVED_LEXEMS.get(possiblyReservedKeyword);
                    return getNewLexem(possiblyReservedKeyword, lexemType);
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
            LexemType lexemType = getTypeIdentifierOrBool(lexemValue);
            return getNewLexem(lexemValue, lexemType);

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

    private Lexem getNewLexem(String reservedKeyword, LexemType lexemType) {
        return Lexem.of(reservedKeyword, lexemType, lineIndex);
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

    private LexemType getTypeIdentifierOrBool(String lexemValue) {
        return "true".equals(lexemValue) || "false".equals(lexemValue)
            ? BOOL
            : IDENTIFIER;
    }

    private void addLexemToResultList(Lexem lexem) {
        resultProgramCodeLexems.add(lexem);
    }

    private void addLexemToResultIdentifierSet(Lexem lexem) {
        resultProgramIdentifierLexems.add(lexem);
    }

    private void addLexemToResultLiteralSet(Lexem lexem) {
        resultProgramLiteralLexems.add(lexem);
    }
}
