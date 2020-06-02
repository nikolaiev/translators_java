package com.translator.lexic.poliz;

import com.translator.lexic.Application;
import com.translator.lexic.lexeme.analyzer.state.LexemeAnalyzer;
import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.util.Utils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolizConverterTest {

    @Test
    void testArithm() throws IOException {
        testPoliz("poliz/program_example_arithm.txt", "a,@,b,c,d,e,/,^,*,f,/,+");
    }

    @Test
    void testBool() throws IOException {
        testPoliz("poliz/program_example_bool.txt", "a,b,+,3,>,!,c,0,==,a,q,-,1,==,||,&&");
    }

    @Test
    void testAssignment() throws IOException {
        testPoliz("poliz/program_example_assignment.txt", "c,a,b,+,=");
    }

    @Test
    void testIfStatement() throws IOException {
        testPoliz("poliz/program_example_if_statement.txt", "a,b,>,m0:,УПХ,a,5,=,m0:");
    }

    @Test
    void testIfStatementNested() throws IOException {
        testPoliz("poliz/program_example_if_statement_nested.txt",
            "a,b,>,m0:,УПХ,a,5,=,23,v,c,/,>,m1:,УПХ,b,2,1,+,=,m1:,m0:");
    }

    @Test
    void testRepeatStatement() throws IOException {
        testPoliz("poliz/program_example_loop.txt",
            "m0:,a,a,1,+,=,a,b,>,m0:,УПХ");
    }

    @Test
    void testRepeatStatementNested() throws IOException {
        testPoliz("poliz/program_example_loop_nested.txt",
            "m0:,a,12,=,m1:,a,b,c,+,=,b,c,1,-,>=,m1:,УПХ,a,b,<,m0:,УПХ");
    }

    @Test
    void testReadStatement() throws IOException {
        testPoliz("poliz/program_example_read.txt",
            "a,b,c,in");
    }

    @Test
    void testWriteStatement_Iden_List() throws IOException {
        testPoliz("poliz/program_example_write_iden_list.txt",
            "a,b,c,out");
    }

    @Test
    void testWriteStatement_Expression() throws IOException {
        testPoliz("poliz/program_example_write_expression.txt",
            "a,b,+,out");
    }

    private void testPoliz(String fileName, String expectedPoliz) throws IOException {
        String programCode = Utils.getFileContentAsString(fileName);
        LexemeAnalyzer lexemeAnalyzer = Application.simpleLexemeAnalyzer(programCode);
        LinkedList<Lexeme> resultProgramCodeLexemes = lexemeAnalyzer.getResultProgramCodeLexemes();

        LinkedList<Lexeme> poliz = PolizConverter.getPoliz(resultProgramCodeLexemes);
        String resultPoliz = poliz.stream().map(Lexeme::getValue).collect(Collectors.joining(","));
        assertEquals(expectedPoliz, resultPoliz);
    }

}