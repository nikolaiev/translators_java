//package com.translator.lexic.analyzer;
//
//import com.translator.lexic.lexeme.Lexeme;
//
//import java.util.LinkedList;
//import java.util.ListIterator;
//
//public class SyntaxAnalizer {
//
//    private final LexemeAnalyzer lexemeAnalyzer;
//    ListIterator<Lexeme> lexemIterator;
//    LinkedList<Lexeme> lexems;
//
//    public SyntaxAnalizer(LexemeAnalyzer lexemeAnalyzer) {
//        this.lexemeAnalyzer = lexemeAnalyzer;
//    }
//
//    public void analyze() {
//        boolean found = false;
//        lexems = lexemeAnalyzer.getResultProgramCodeLexemes();
//        lexemIterator = lexems.listIterator();
//        Lexeme Lexeme = nextLexeme();
//        if(getCodeOfCurrentLexeme()==1) { //if PROGRAM loopLexeme
//            Lexeme = nextLexeme();
//            if (getCodeOfCurrentLexeme() == 30) { //if IDENTIFIER
//                Lexeme = nextLexeme();
//                if (getCodeOfCurrentLexeme() == 17) { //if ";"
//                    Lexeme = nextLexeme();
//                    if (getCodeOfCurrentLexeme() == 2) { //if VAR Lexeme
//                        if (analyzeListDeclarations()) {
//                            Lexeme = currentLexem();
//                            if (getCodeOfCurrentLexeme() == 3) { //if BEGIN loopLexeme
//                                if(analyzeListOperators()) { //if all operators are correct
//                                    Lexeme = currentLexem();
//                                    if (getCodeOfCurrentLexeme() == 4) { //if END
//                                        if(nextLexeme()==null)
//                                            found = true;
//                                        else warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(), Warning.EXTRA_OPERATORS));
//                                    } else
//                                        warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(), Warning.WRONG_PROGRAM_COMPLETION));
//                                }
//                                else {}
//                            } else
//                                warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(), Warning.KEYWORD_BEGIN_MISSED));
//                        }
//                        else warnings.add(new Warning(Warning.Type.ERROR, lexems.get(lexemIterator.nextIndex()-1).getLine(),Warning.WRONG_DECLARATION_LIST));
//                    }
//                    else warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(),Warning.NO_VAR));
//                }
//                else warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(), Warning.SEMICOLON_EXPECTED));
//            }
//            else warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(),Warning.NO_PROGRAM_NAME));
//        }
//        else warnings.add(new Warning(Warning.Type.ERROR, Lexeme.getLine(),Warning.KEYWORD_PROGRAM_MISSED));
//    }
//
//    private int getCodeOfCurrentLexeme (){
//        Lexeme lexeme= lexems.get(lexemIterator.nextIndex()-1);
//        int code =-1;
//        if(lexeme != null)
//            code = lexeme.getCode();
//        return code;
//    }
//
//    private Lexeme nextLexeme() {
//        if(lexemIterator.hasNext())
//            return lexemIterator.next();
//        else return null;
//    }
//}
