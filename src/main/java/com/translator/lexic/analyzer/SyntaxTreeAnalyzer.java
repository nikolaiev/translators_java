package com.translator.lexic.analyzer;

import com.translator.lexic.lexeme.Lexeme;
import com.translator.lexic.lexeme.LexemeType;
import com.translator.lexic.syntax.ProgramTree;
import com.translator.lexic.syntax.units.SyntaxUnit;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class SyntaxTreeAnalyzer {

    public void analyze(LinkedList<Lexeme> lexemes) {
        lexemes = getLexemesWithoutWhitespaces(lexemes);
        ProgramTree tree = new ProgramTree();
        tree.validateSyntax(lexemes);
        if(!SyntaxUnit.errors.isEmpty()) {
            SyntaxUnit.errors.stream().distinct().forEach(er -> System.out.println(er));
        }
        else {
            System.out.println("Syntax is valid");
        }
    }

    private LinkedList<Lexeme> getLexemesWithoutWhitespaces(LinkedList<Lexeme> lexemes) {
        //removing white spaces lexems
        lexemes = lexemes
            .stream()
            .filter(lexeme -> !lexeme.getType().equals(LexemeType.WHITE))
            .collect(Collectors.toCollection(LinkedList::new));
        return lexemes;
    }
}
