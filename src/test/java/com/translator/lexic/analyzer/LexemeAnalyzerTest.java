package com.translator.lexic.analyzer;

import com.translator.lexic.lexeme.Lexeme;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

class LexemeAnalyzerTest {

    @Test
    void analyzeCode() throws IOException {
        String programCode = getFileContentAsString("program_example.txt");
        LexemeAnalyzer analyzer = new LexemeAnalyzer(programCode);
        analyzer.analyzeCode();

        System.out.println("Analyzing result");
        printAllLexems(analyzer);
        System.out.println("\n\n\n");
        System.out.println("Identifiers");
        printIdentifiers(analyzer);
        System.out.println("\n\n\n");
        System.out.println("Literals");
        printLiterals(analyzer);
    }

    private void printLiterals(LexemeAnalyzer analyzer) {
        System.out.println("-----------------------");
        System.out.printf("|%-10s|%-10s|\n", "value", "code");
        System.out.println("-----------------------");
        analyzer.getResultProgramLiteralLexemes()
            .forEach(literal -> {
                System.out.printf("|%-10s|%-10s|\n", literal.getValue(), analyzer.getLiteralIndex(literal));
            });
        System.out.println("-----------------------");
    }

    private void printIdentifiers(LexemeAnalyzer analyzer) {
        System.out.println("-----------------------");
        System.out.printf("|%-10s|%-10s|\n", "value", "code");
        System.out.println("-----------------------");
        analyzer.getResultProgramIdentifierLexemes()
            .forEach(identifier -> {
                System.out.printf("|%-10s|%-10s|\n", identifier.getValue(), analyzer.getIdentifierIndex(identifier));
            });
        System.out.println("-----------------------");
    }

    private void printAllLexems(LexemeAnalyzer analyzer) {
        List<Lexeme> lexemeEtranceAmoun = analyzer.getResultProgramCodeLexemes();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(format("|%-10s|%-15s|%-8s|%-8s|%-15s|%-15s|", "Lexeme", "type", "code", "line", "index idetifier", "index literal"));
        System.out.println("-----------------------------------------------------------------------------");
        lexemeEtranceAmoun.stream()
            .forEach(lexem -> {
                System.out.println(format("|%-10s|%-15s|%-8s|%-8s|%-15s|%-15s|",
                    lexem.getValue()
                        .replace(" ", "\\s")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t"),
                    lexem.getType(), lexem.getCode(), lexem.getLineIndex(),
                    emptyIfLessThanZero(analyzer.getIdentifierIndex(lexem)),
                    emptyIfLessThanZero(analyzer.getLiteralIndex(lexem))));
            });
        System.out.println("-----------------------------------------------------------------------------");
    }

    private String emptyIfLessThanZero(int i) {
        if (i < 0) {
            return "";
        }
        return String.valueOf(i);
    }

    private Map<Lexeme, Integer> getLexemEntranceAmountMap(List<Lexeme> resultProgramCodeLexemes) {
        Map<Lexeme, Integer> resultMap = new HashMap<>();
        resultProgramCodeLexemes.forEach(lexem -> {
            Integer previousValue = resultMap.get(lexem);
            resultMap.put(lexem, previousValue == null ? 1 : previousValue + 1);
        });
        return resultMap;
    }

    private static String getFileContentAsString(String fileName) throws IOException {
        InputStream resourceAsStream = LexemeAnalyzerTest.class.getClassLoader().getResourceAsStream(fileName);
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
            (resourceAsStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }
}