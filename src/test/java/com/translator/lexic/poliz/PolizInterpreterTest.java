package com.translator.lexic.poliz;

import com.translator.lexic.Application;
import com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer;
import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

class PolizInterpreterTest {


    @Test
    void testArithmeticalStatement_Expression() throws IOException {
        testPoliz("poliz_interpreter/program_example_arithm.txt");
    }

    private void testPoliz(String fileName) throws IOException {
        String programCode = Utils.getFileContentAsString(fileName);
        LexemeAnalyzer lexemeAnalyzer = Application.simpleLexemeAnalyzer(programCode);
        LinkedList<Lexeme> resultProgramCodeLexemes = lexemeAnalyzer.getResultProgramCodeLexemes();

        LinkedList<Lexeme> poliz = PolizConverter.getPoliz(resultProgramCodeLexemes);
        PolizInterpreter.interpret(poliz);
    }
}