package com.translator.lexic;

import com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer;
import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.poliz.PolizConverter;
import com.translator.lexic.poliz.PolizInterpreter;
import com.translator.lexic.syntax.descending.analyzer.SyntaxTreeAnalyzer;
import com.translator.lexic.syntax.magazine.MagazineAutomaton;
import com.translator.lexic.util.Utils;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.translator.lexic.util.Utils.getFileContentAsString;
import static java.lang.String.format;

public class Application {

    public static void main(String[] args) throws IOException {
        String programCode = getFileContentAsString("poliz_interpreter/program_example_arithm.txt");

        System.out.println("Enter 1 to use Antrl for lexical analize; Otherwise - standard.\n");
        int key = 1;// getKey();
        //LEXEME ANALYZER
        //if (key == 1) {
        //   antrl4LexemeAnalyzer(programCode);
        //} else {
        LexemeAnalyzer lexemeAnalyzer = simpleLexemeAnalyzer(programCode);
        printLexemeAnalyzerResult(lexemeAnalyzer);
        //}

        //SYNTAX ANALYZER
        //antrl4SyntaxAnalyzer(programCode);
        LinkedList<Lexeme> resultProgramCodeLexemes = lexemeAnalyzer.getResultProgramCodeLexemes();
        //TODO uncomment
        //magazineSyntaxAnalyzer(resultProgramCodeLexemes);
        //treeSyntaxAnalyzer(lexemeAnalyzer);
        poliz(resultProgramCodeLexemes);

    }

    private static void poliz(LinkedList<Lexeme> resultProgramCodeLexemes) {
        //TODO show something
        LinkedList<Lexeme> poliz = PolizConverter.getPoliz(resultProgramCodeLexemes);
        PolizInterpreter.interpret(poliz);
    }

    private static void antrl4LexemeAnalyzer(String programCode) {
        HelloLexer lexer = new HelloLexer(CharStreams.fromString(programCode));

        Token token = lexer.nextToken();
        while (token.getType() != HelloLexer.EOF) {
            System.out.println("\t" + token.getType() + "\t" + HelloLexer.ruleNames[token.getType() - 1] + "\t\t" + token.getText());
            token = lexer.nextToken();
        }
    }

    private static int getKey() {

        Scanner keyboard = new Scanner(System.in);
        System.out.println("enter an integer");
        return keyboard.nextInt();
    }

    private static void antrl4SyntaxAnalyzer(String programCode) {
        HelloLexer lexer = new HelloLexer(CharStreams.fromString(programCode));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        HelloParser parser = new HelloParser(tokens);
        ParseTree tree = parser.r_id();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new HelloBaseListener(), tree);
    }

    public static LexemeAnalyzer simpleLexemeAnalyzer(String programCode) {
        LexemeAnalyzer lexemeAnalyzer = new LexemeAnalyzer(programCode);
        lexemeAnalyzer.analyzeCode();
        return lexemeAnalyzer;
    }

    private static void treeSyntaxAnalyzer(LexemeAnalyzer lexemeAnalyzer) {
        SyntaxTreeAnalyzer syntaxTreeAnalyzer = new SyntaxTreeAnalyzer(lexemeAnalyzer.getResultProgramCodeLexemes());
        syntaxTreeAnalyzer.analyze();
    }

    private static void magazineSyntaxAnalyzer(LinkedList<Lexeme> lexemes) {
        MagazineAutomaton magazineAutomaton = new MagazineAutomaton(lexemes);
        magazineAutomaton.analyze();
        if (magazineAutomaton.getWarnings().isEmpty()) {
            //перевіряємо пустоту стека
            if (magazineAutomaton.getStack().size() == 1 &&
                magazineAutomaton.getStack().getFirst() == -1) {
                System.out.println("Syntax is valid");
            } else {
                System.out.println("Syntax error stack is not empty");
                System.out.println(magazineAutomaton.getStack());
            }
        } else {
            System.out.println("Syntax errors found : ");
            magazineAutomaton.getWarnings().forEach(System.out::println);
        }
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

    private static String emptyIfLessThanZero(int i) {
        if (i < 0) {
            return "";
        }
        return String.valueOf(i);
    }
}
