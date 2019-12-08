package com.translator.lexic;

import com.translator.lexic.lexeme.analyzer.LexemeAnalyzer;
import com.translator.lexic.syntax.analyzer.SyntaxTreeAnalyzer;
import com.translator.lexic.lexeme.model.Lexeme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.String.format;

public class Application {

    public static void main(String[] args) throws IOException {
        //LEXEME ANALYZER
        String programCode = getFileContentAsString("program_example.txt");
        LexemeAnalyzer lexemeAnalyzer = new LexemeAnalyzer(programCode);
        lexemeAnalyzer.analyzeCode();
        printLexemeAnalyzerResult(lexemeAnalyzer);

        //System.out.println("\nPress any key to start syntax analyzing ...");
        //PAUSE TODO comment if needed
        //System.in.read(); //pause to key press until Syntax analyzer start
        System.out.println("\nStarted syntax analyzing ...");

        //SYNTAX ANALYZER
        SyntaxTreeAnalyzer syntaxTreeAnalyzer = new SyntaxTreeAnalyzer();
        syntaxTreeAnalyzer.analyze(lexemeAnalyzer.getResultProgramCodeLexemes());
    }

    private static void printLexemeAnalyzerResult(LexemeAnalyzer lexemeAnalyzer) {
        System.out.println("Analyzing result");
        printAllLexems(lexemeAnalyzer);
        System.out.println("\n\n\n");
        System.out.println("Identifiers");
        printIdentifiers(lexemeAnalyzer);
        System.out.println("\n\n\n");
        System.out.println("Literals");
        printLiterals(lexemeAnalyzer);
    }

    private static String getFileContentAsString(String fileName) throws IOException {
        InputStream resourceAsStream = Application.class.getClassLoader().getResourceAsStream(fileName);
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

    private static void printLiterals(LexemeAnalyzer analyzer) {
        System.out.println("-----------------------");
        System.out.printf("|%-10s|%-10s|\n", "value", "code");
        System.out.println("-----------------------");
        analyzer.getResultProgramLiteralLexemes()
            .forEach(literal -> {
                System.out.printf("|%-10s|%-10s|\n", literal.getValue(), analyzer.getLiteralIndex(literal));
            });
        System.out.println("-----------------------");
    }

    private static void printIdentifiers(LexemeAnalyzer analyzer) {
        System.out.println("-----------------------");
        System.out.printf("|%-10s|%-10s|\n", "value", "code");
        System.out.println("-----------------------");
        analyzer.getResultProgramIdentifierLexemes()
            .forEach(identifier -> {
                System.out.printf("|%-10s|%-10s|\n", identifier.getValue(), analyzer.getIdentifierIndex(identifier));
            });
        System.out.println("-----------------------");
    }

    private static void printAllLexems(LexemeAnalyzer analyzer) {
        List<Lexeme> lexemeEtranceAmoun = analyzer.getResultProgramCodeLexemes();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println(format("|%-10s|%-15s|%-8s|%-8s|%-15s|%-15s|", "Lexeme", "type", "code", "line", "index idetifier", "index literal"));
        System.out.println("-----------------------------------------------------------------------------");
        lexemeEtranceAmoun
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

    private static String  emptyIfLessThanZero(int i) {
        if (i < 0) {
            return "";
        }
        return String.valueOf(i);
    }
}
