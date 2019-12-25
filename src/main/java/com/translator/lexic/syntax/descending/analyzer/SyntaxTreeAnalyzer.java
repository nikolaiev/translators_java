package com.translator.lexic.syntax.descending.analyzer;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.SyntaxAnalyzer;
import com.translator.lexic.syntax.descending.units.ProgramSyntaxStructureTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class SyntaxTreeAnalyzer implements SyntaxAnalyzer {

    private static Logger logger = LoggerFactory.getLogger(SyntaxTreeAnalyzer.class);

    private LinkedList<Lexeme> lexemes;

    @Override
    public void analyze() {
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
