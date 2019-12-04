package com.translator.lexic.syntax.units;

import com.translator.lexic.exception.SyntaxAnalyzerException;
import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Data
public abstract class SyntaxUnit {

    private Lexeme lexeme;
    private LexemeType lexemeType;
    private LinkedList<SyntaxUnit> exactSyntax;
    private LinkedList<SyntaxUnit> syntaxOptions;
    private Lexeme loopLexeme; //if SynatUnit is looped
    private boolean isLoopMandatory = true;

    public SyntaxUnit() {
    }

    public SyntaxUnit(Lexeme lexeme) {
        this.lexeme = lexeme;
    }

    public SyntaxUnit(LexemeType type) {
        this.lexemeType = type;
    }

    public void validateSyntax(LinkedList<Lexeme> resultProgramCodeLexemes) {
        try {
            if (lexemeType != null) {
                validateLexemeType(resultProgramCodeLexemes);
            } else if (lexeme != null) {
                validateLexeme(resultProgramCodeLexemes);
            } else if (exactSyntax != null) {
                validateExactSyntax(resultProgramCodeLexemes);
            } else if (syntaxOptions != null) {
                validateOptionsSyntax(resultProgramCodeLexemes);
            }

            //recursion
            if (loopLexeme != null) {
                if (loopLexeme.equals(resultProgramCodeLexemes.getFirst())) {
                    if(isLoopMandatory) {
                        resultProgramCodeLexemes.removeFirst();
                        this.validateSyntax(resultProgramCodeLexemes);
                    }else {
                        LinkedList<Lexeme> lexemesCopy = new LinkedList<>(resultProgramCodeLexemes);
                        lexemesCopy.removeFirst();
                        try{
                           this.validateSyntax(lexemesCopy);
                       }
                       catch (SyntaxAnalyzerException ignored){
                           //loop ended since it's not mandatory
                           return;
                       }
                        alignListsSize(resultProgramCodeLexemes, lexemesCopy);
                    }
                }
            }
        }catch (NoSuchElementException e){ //if resultProgramCodeLexemes will be empty
            throw new SyntaxAnalyzerException("Reached out of lexems",e);
        }
    }

    private void alignListsSize(LinkedList<Lexeme> resultProgramCodeLexemes, LinkedList<Lexeme> lexemesCopy) {
        int resultSize = lexemesCopy.size();
        int prevSize = resultProgramCodeLexemes.size();
        int itemsToRemove = prevSize - resultSize;
        //update main list
        while (itemsToRemove > 0) {
            resultProgramCodeLexemes.removeFirst();
            itemsToRemove = itemsToRemove - 1;
        }
    }

    private void validateExactSyntax(LinkedList<Lexeme> resultProgramCodeLexemes) {
        exactSyntax.forEach(su -> su.validateSyntax(resultProgramCodeLexemes));
    }

    private void validateLexeme(LinkedList<Lexeme> resultProgramCodeLexemes) {
        Lexeme first = resultProgramCodeLexemes.getFirst();
        if (first.getCode() == this.getLexeme().getCode()) {
            resultProgramCodeLexemes.removeFirst();
        } else {
            throw new SyntaxAnalyzerException(format("Syntax error. Expected %s, but found %s at line %s",
                this.getLexeme().getValue(), first, first.getLineIndex()));
        }
    }

    private void validateLexemeType(LinkedList<Lexeme> resultProgramCodeLexemes) {
        Lexeme first = resultProgramCodeLexemes.getFirst();
        if (first.getType().equals(LexemeType.IDENTIFIER)) {
            resultProgramCodeLexemes.removeFirst();
        } else {
            throw new SyntaxAnalyzerException(format("Syntax error. Expected lexeme of type %s, but found %s of type %s at line %s",
                this.getLexemeType(), first, first.getType(), first.getLineIndex()));
        }
    }

    private void validateOptionsSyntax(LinkedList<Lexeme> resultProgramCodeLexemes) {
        int sizeBefore = resultProgramCodeLexemes.size();
        for (SyntaxUnit option : syntaxOptions) {
            //we cannot operate on main list
            LinkedList<Lexeme> lexemesCopy = new LinkedList<>(resultProgramCodeLexemes);
            try {
                option.validateSyntax(lexemesCopy);
            } catch (SyntaxAnalyzerException e) { //option did not match
//                System.out.println(e.getMessage());
                continue;
            }
            //option did match
            alignListsSize(resultProgramCodeLexemes, lexemesCopy);
            break; //since further processing is not needed
        }
        //check main list was updated since at least one option should have been applied
        int sizeAfter = resultProgramCodeLexemes.size();
        if (sizeBefore == sizeAfter) {
            Lexeme first = resultProgramCodeLexemes.getFirst();
            throw new SyntaxAnalyzerException(format("Syntax error. Expected one of %s, but found %s at line %s",
                getReadableOptionsList(), first, first.getLineIndex()));
        }
    }

    private List<?> getReadableOptionsList() {
        return this.getSyntaxOptions().stream().map(option-> {
                Class<? extends SyntaxUnit> optionClass = option.getClass();
                if(optionClass.equals(ReservedKeyword.class)){
                return ((ReservedKeyword)option).getLexeme();
            }
            return optionClass;
        }
        ).collect(toList());
    }
}
