package com.translator.lexic.lexeme.analyzer.state.states;

import com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer;
import com.translator.lexic.lexeme.analyzer.state.states.middle.State0;
import com.translator.lexic.lexeme.exception.UnexpectedLexemException;
import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.lexeme.model.ReservedLexem;
import com.translator.lexic.lexeme.util.RegexHolder;
import com.translator.lexic.syntax.units.declaration.ReservedKeyword;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer.PROGRAM_CODE;
import static com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer.currentLine;
import static java.lang.String.format;

@Data
@Slf4j
public abstract class AbstractState {


    public String regex;
    public String nextStateRegexTrigger;
    public boolean isCyclic;
    public boolean isTerminal;
    public boolean isTerminalNotReturned;
    public AbstractState nextState;
    public AbstractState nextStateFast;

    public abstract void init();

    public void process(String buffer) {
//        log.info("Inside {} buffer {}", this.getClass(), buffer);
        if (isTerminal) {
            processReturningBackOnOneSymbolTerminalState(buffer);
            return;
        }
        if(isTerminalNotReturned){
            processNotReturningTerminalState(buffer);
            return;
        }
        String character = getNextCaherFromCodeAndDecreaseCode();
        if (isRegexMatches(character)) {
            if (isCyclic) {
                this.process(buffer + character);
            } else {
                nextState.process(buffer + character);
            }
        } else {
            if (isNextStateRegexMatches(character)) {
                nextState.process(buffer + character);
            } else if (nextStateFast != null) {
                nextStateFast.process(buffer + character);
            } else if (isCyclic) {
                nextState.process(buffer + character);
            } else {
                //TODO think about another exception
                throw new IllegalStateException("State was not properly built " + buffer);
            }
        }
    }

    private String getNextCaherFromCodeAndDecreaseCode() {
        if(PROGRAM_CODE.length()!=0) {
            String character = String.valueOf(PROGRAM_CODE.charAt(0));
            PROGRAM_CODE = PROGRAM_CODE.substring(1);
            return character;
        }
        throw new IllegalStateException("Exception");
    }

    private void processNotReturningTerminalState(String buffer){
        addLexemeToResult(buffer);
        if(PROGRAM_CODE.length()!=0) {
            String character = getNextCaherFromCodeAndDecreaseCode();
            //start state diagram from the beginning
            State0.getInstance().process(character);
        }
    }

    private void processReturningBackOnOneSymbolTerminalState(String buffer){
        String lastCharacter = String.valueOf(buffer.charAt(buffer.length() - 1));
        String lexeme = buffer.substring(0,buffer.length()-1);
        addLexemeToResult(lexeme);
        //start state diagram from the beginning
        State0.getInstance().process(lastCharacter);
    }

    private void addLexemeToResult(String lexemeStr) {
        LexemeType lexemeType = ReservedLexem.RESERVED_LEXEMS.get(lexemeStr);
        if(lexemeType!=null){
            LexemeAnalyzer.resultProgramCodeLexemes.add(getNewLexem(lexemeStr,lexemeType));
            return;
        }

        if(lexemeStr.matches(RegexHolder.IDENTIFIER_REGEX)){
            if(lexemeStr.matches("true|false")){
                Lexeme newLexem = getNewLexem(lexemeStr, LexemeType.BOOL);
                LexemeAnalyzer.resultProgramCodeLexemes.add(newLexem);
            }
            else {
                Lexeme newLexem = getNewLexem(lexemeStr, LexemeType.IDENTIFIER);
                LexemeAnalyzer.resultProgramCodeLexemes.add(newLexem);
                LexemeAnalyzer.resultProgramIdentifierLexemes.add(newLexem);
            }
            return;
        }
        if(lexemeStr.matches(RegexHolder.UNSIGNED_NUMBER_REGEX)){
            Lexeme newLexem = getNewLexem(lexemeStr, LexemeType.NUMBER);
            LexemeAnalyzer.resultProgramCodeLexemes.add(newLexem);
            LexemeAnalyzer.resultProgramLiteralLexemes.add(newLexem);
            return;
        }

        if(lexemeStr.matches(RegexHolder.STRING_REGEX)){
            Lexeme newLexem = getNewLexem(lexemeStr, LexemeType.STRING);
            LexemeAnalyzer.resultProgramCodeLexemes.add(newLexem);
            LexemeAnalyzer.resultProgramLiteralLexemes.add(newLexem);
        }
        else{
            throw new UnexpectedLexemException(format("Unexpected character near %s at line %s",lexemeStr, currentLine ));
        }
    }

    private boolean isRegexMatches(String character) {
        return regex != null && character.matches(regex);
    }

    private boolean isNextStateRegexMatches(String character) {
        return nextStateRegexTrigger != null && character.matches(nextStateRegexTrigger);
    }


    private Lexeme getNewLexem(String reservedKeyword, LexemeType lexemeType) {
        return Lexeme.of(reservedKeyword, lexemeType, LexemeAnalyzer.currentLine);
    }

}
