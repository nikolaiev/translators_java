package com.translator.lexic;

import com.translator.lexic.analyzer.LexemeAnalyzer;
import com.translator.lexic.analyzer.SyntaxTreeAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Application {

    public static void main(String[] args) throws IOException {
        String programCode = getFileContentAsString("program_example.txt");
        LexemeAnalyzer lexemeAnalyzer = new LexemeAnalyzer(programCode);
        lexemeAnalyzer.analyzeCode();

        SyntaxTreeAnalyzer syntaxTreeAnalyzer = new SyntaxTreeAnalyzer();
        syntaxTreeAnalyzer.analyze(lexemeAnalyzer.getResultProgramCodeLexemes());
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
}
