package com.translator.lexic.syntax.descending.units;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.syntax.descending.exception.SyntaxAnalyzerException;
import com.translator.lexic.syntax.descending.units.declaration.DeclarationList;
import com.translator.lexic.syntax.descending.units.declaration.Identifier;
import com.translator.lexic.syntax.descending.units.declaration.ReservedKeyword;
import com.translator.lexic.syntax.descending.units.statement.StatementList;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

//Model of syntax
public class ProgramSyntaxStructureTree {

    public static List<SyntaxErrorHolder> errorHolder;

    private static SyntaxUnit unitsTree = new SyntaxUnit() {{
        this.setName("ProgramSyntaxStructureTree");
        this.setExactSyntax(new LinkedList<>() {{
            add(new ReservedKeyword("program"));
            add(new Identifier());
            add(new ReservedKeyword("var"));
            add(new DeclarationList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("{"));
            add(new StatementList());
            add(new ReservedKeyword(";"));
            add(new ReservedKeyword("}"));
        }});
    }};

    public static void validateSyntax(LinkedList<Lexeme> lexemes) {
        errorHolder = new LinkedList<>(); //clea up shared error holder
        try {
            unitsTree.validateSyntax(lexemes);
        } catch (SyntaxAnalyzerException e) {
            saveSyntaxErrorState(e, lexemes.size());
        } catch (StackOverflowError e) {
            saveSyntaxErrorState(new SyntaxAnalyzerException("Error while parsing lexemes before " + lexemes.getFirst()), lexemes.size());
        }
    }

    public static void saveSyntaxErrorState(SyntaxAnalyzerException exception, int size) {
        SyntaxErrorHolder syntaxErrorHolder = new SyntaxErrorHolder();
        syntaxErrorHolder.setLexemesLeftCount(size);
        syntaxErrorHolder.setMessage(exception.getMessage());
        errorHolder.add(syntaxErrorHolder);
    }

    @Data
    public static class SyntaxErrorHolder {
        public String message;
        public int lexemesLeftCount;
    }
}
