package com.translator.lexic.syntax.analyzer;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.units.ProgramSyntaxStructureTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class SyntaxTreeAnalyzer {

    private static Logger logger = LoggerFactory.getLogger(SyntaxTreeAnalyzer.class);

    public void analyze(LinkedList<Lexeme> lexemes) {
        lexemes = getLexemesWithoutWhitespaces(lexemes);
        ProgramSyntaxStructureTree.validateSyntax(lexemes);

        if (!lexemes.isEmpty()) {
            int minLexemesLeftUnprocessed = ProgramSyntaxStructureTree.errorHolder.stream()
                .map(eh -> eh.getLexemesLeftCount())
                .min(Integer::compareTo)
                .get();
            ProgramSyntaxStructureTree.errorHolder.stream().filter(eh -> eh.getLexemesLeftCount() == minLexemesLeftUnprocessed).distinct()
                .forEach(e -> logger.error(e.getMessage()));
        } else {
            logger.info("Syntax is valid");
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
