package com.translator.lexic.syntax.units;

import com.translator.lexic.exception.SyntaxAnalyzerException;
import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Data
public abstract class SyntaxUnit {

//    public static int formatterInt = 0;
    public static LinkedList<String> errors = new LinkedList<>();
    private String name = "---";
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
//        System.out.println(resultProgramCodeLexemes);
//        for (int i = 0; i < formatterInt; i++) {
//            System.out.print(" ");
//        }
//        System.out.println("->" + this.getName());
        int sizeBefore = resultProgramCodeLexemes.size();
        try {
            if (lexemeType != null) {
                validateLexemeType(resultProgramCodeLexemes);
            } else if (lexeme != null) {
                validateLexeme(resultProgramCodeLexemes);
            } else if (exactSyntax != null) {
//                formatterInt += 2;
                validateExactSyntax(resultProgramCodeLexemes);
//                formatterInt -= 2;
            } else if (syntaxOptions != null) {
//                formatterInt += 2;
                validateOptionsSyntax(resultProgramCodeLexemes);
//                formatterInt -= 2;
            }

            //recursion
            if (loopLexeme != null) {
                if (loopLexeme.equals(resultProgramCodeLexemes.getFirst())) {
                    if (isLoopMandatory) {
                        resultProgramCodeLexemes.removeFirst();
                        this.validateSyntax(resultProgramCodeLexemes);
                    } else {
                        LinkedList<Lexeme> lexemesCopy = new LinkedList<>(resultProgramCodeLexemes);
                        lexemesCopy.removeFirst();
                        try {
                            this.validateSyntax(lexemesCopy);
                        } catch (SyntaxAnalyzerException ignored) {
                            //loop ended since it's not mandatory
                            return;
                        }
                        alignListsSize(resultProgramCodeLexemes, lexemesCopy);
                    }
                }
            }else {
                //throw exception if nothing was validated
                assertLexemesWereProceed(resultProgramCodeLexemes, sizeBefore);
            }
        } catch (NoSuchElementException e) { //if resultProgramCodeLexemes will be empty
            throw new SyntaxAnalyzerException("Reached out of lexems", e);
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
        if (first.getType().equals(getLexemeType())) {
            resultProgramCodeLexemes.removeFirst();
        } else {
            throw new SyntaxAnalyzerException(format("Syntax error. Expected lexeme of type %s, but found %s of type %s at line %s",
                this.getLexemeType(), first, first.getType(), first.getLineIndex()));
        }
    }

    private void validateOptionsSyntax(LinkedList<Lexeme> resultProgramCodeLexemes) {
        for (SyntaxUnit option : syntaxOptions) {
            //we cannot operate on main list
            LinkedList<Lexeme> lexemesCopy = new LinkedList<>(resultProgramCodeLexemes);
            try {
                option.validateSyntax(lexemesCopy);
            } catch (SyntaxAnalyzerException e) { //option did not match
                if(resultProgramCodeLexemes.size()==0
                    && e.getMessage()!="Reached out of lexems"){
                    errors.addFirst(e.getMessage());
                }
                //System.out.println(e.getMessage());
                continue;
            }
            //option did match
            alignListsSize(resultProgramCodeLexemes, lexemesCopy);
            break; //since further processing is not needed
        }
    }

    private void assertLexemesWereProceed(LinkedList<Lexeme> resultProgramCodeLexemes, int sizeBefore) {
        //check main list was updated since at least one option should have been applied
        int sizeAfter = resultProgramCodeLexemes.size();
        if (sizeBefore <= sizeAfter) {
            if(sizeAfter!=0) {
                Lexeme first = resultProgramCodeLexemes.getFirst();
                throw new SyntaxAnalyzerException(format("Syntax error. Expected one of %s, but found %s at line %s",
                    getReadableOptionsList(), first, first.getLineIndex()));
            }
            throw new SyntaxAnalyzerException(format("Syntax error. Expected one of %s, but reached out of lexemes",
                getReadableOptionsList()));
        }
    }

    public List<Object> getReadableOptionsList() {
        return this.getSyntaxOptions()
            .stream()
            .map(option -> {
                    if (option.getLexeme() != null) {
                        return List.of(option.getLexeme());
                    }
                    if (option.getLexemeType() != null) {
                        return List.of(option.getLexemeType());
                    }
                    if (option.exactSyntax != null) {
                        return option.exactSyntax.stream().map(SyntaxUnit::getName)
                            .filter(n->!n.equals("---"))
                            .collect(Collectors.toList());
                    }
                    if (option.syntaxOptions != null) {
                        return option.syntaxOptions.stream().map(SyntaxUnit::getName)
                            .filter(n->!n.equals("---"))
                            .collect(Collectors.toList());
                    }
                    return Collections.emptyList();

                }
            )
            .flatMap(Collection::stream)
            .filter(Objects::nonNull).collect(toList());
    }
}
