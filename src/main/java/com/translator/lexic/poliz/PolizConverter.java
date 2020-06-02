package com.translator.lexic.poliz;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Slf4j
public class PolizConverter {

    static HashMap<String, Integer> lexemePriorities = new HashMap<>() {{
        put("(", 0);
        put("if", 0);
//        put(";", 0); //TODO think about it
        put(")", 1);
        put("then", 1);
        put("=", 2);
        put("||", 3);
        put("&&", 4);
        put("!", 5);

        put("<", 6); //20
        put("<=", 6); //21
        put("==", 6); //22
        put(">=", 6); //23
        put("!=", 6); //24
        put(">", 6); //26''

        put("+", 7);
        put("-", 7);
        put("@", 8); //УНАРНИЙ МІНУС
        put("*", 8);
        put("/", 8);

//        put("%", 9);
        put("^", 9);
    }};


    static Lexeme UNARY_MINUS = new Lexeme("@", LexemeType.KEYWORD);
    //БП - безумовний перехід
    static Lexeme GOTO = new Lexeme("БП", LexemeType.KEYWORD);
    //УПХ - умовний перехід по хибності
    static Lexeme GOTO_ON_FAIL = new Lexeme("УПХ", LexemeType.KEYWORD);
    static LinkedList<Lexeme> GOTOS = new LinkedList<>();
    static int gotoNumber = 0;

    public static LinkedList<Lexeme> getPoliz(LinkedList<Lexeme> polizLexemes) {
        LinkedHashMap<String, String> resultPoliz = new LinkedHashMap<>();

        LinkedList<Lexeme> exit = new LinkedList<Lexeme>();
        LinkedList<Lexeme> stack = new LinkedList<Lexeme>();
        //remove whitespaces
        polizLexemes = polizLexemes
            .stream().filter(l -> l.getType() != LexemeType.WHITE).collect(Collectors.toCollection(LinkedList::new));
        //we need to check prev lexeme for унарний мінус
        Lexeme previousLexeme = null;
        while (!polizLexemes.isEmpty()) {
            Lexeme lexeme = polizLexemes.removeFirst();
            System.out.println("");
            System.out.println("EXIT " + formatExit(exit));
            System.out.println("STACK " + formatExit(stack));
            System.out.println("InputLexeme:" + lexeme);

            LexemeType type = lexeme.getType();
            String lexemeValue = lexeme.getValue();

            if (lexemeValue.equals("}") || lexemeValue.equals("{") || lexemeValue.equals(";")) {
                //noop
                //skip this symbols for poliz
            } else if (lexemeValue.equals("-")) {//unary minus
                //previousLexeme can be null
                LexemeType previousLexemeType = previousLexeme != null ? previousLexeme.getType() : null;
                String previousLexemeValue = previousLexeme != null ? previousLexeme.getValue() : null;
                if (previousLexemeType != LexemeType.IDENTIFIER
                    && previousLexemeType != LexemeType.NUMBER
                    && !")".equals(previousLexemeValue)) {
                    processInputTokenWithPriority(exit, stack, UNARY_MINUS);
                } else {
                    processInputTokenWithPriority(exit, stack, lexeme);
                }
            } else if (lexemeValue.equals("(")) {
                stack.add(lexeme);

            } else if (lexemeValue.equals(")")) {
                while (true) {
                    Lexeme lastOnStack = stack.removeLast();
                    if (lastOnStack.getValue().equals("(")) break;
                    exit.add(lastOnStack);
                }
            } else if (lexemeValue.equals("if")) { //IF Statement
                stack.add(lexeme);

            } else if (lexemeValue.equals("then")) {
                while (true) {
                    Lexeme lastOnStack = stack.getLast();
                    if (lastOnStack.getValue().equals("if")) {
                        String _goto = "m" + gotoNumber + ":";
                        gotoNumber++;
                        //mi УПХ
                        final Lexeme _gotoLexeme = new Lexeme(_goto, LexemeType.KEYWORD);
                        //just a list of gotos
                        GOTOS.add(_gotoLexeme);
                        exit.add(_gotoLexeme);
                        exit.add(GOTO_ON_FAIL);
                        //mi
                        stack.add(_gotoLexeme);
                        break;
                    }
                    stack.removeLast();
                    exit.add(lastOnStack);
                }
            } else if (lexemeValue.equals("fi")) {
                while (true) {
                    Lexeme lastOnStack = stack.removeLast();
                    if (lastOnStack.getValue().equals("if")) {
                        //exit.add(GOTOS.removeLast());
                        break;
                    }
                    exit.add(lastOnStack);
                }
            } else if (type == LexemeType.IDENTIFIER
                || type == LexemeType.STRING
                || type == LexemeType.BOOL
                || type == LexemeType.NUMBER) {
                exit.add(lexeme);
            } else {
                processInputTokenWithPriority(exit, stack, lexeme);
            }

            previousLexeme = lexeme;
        }
        while (!stack.isEmpty()) {
            exit.add(stack.removeLast());
        }
        System.out.println("Exit:" + formatExit(exit));
        System.out.println("Stack:" + formatExit(stack));

        return exit;
    }

    //пункт 2 алгоритму Дейкстри
    private static void processInputTokenWithPriority(LinkedList<Lexeme> exit, LinkedList<Lexeme> stack, Lexeme lexeme) {
        boolean shouldRepeatThisPunct;
        do {
            if (stack.isEmpty()) {
                stack.add(lexeme);
                shouldRepeatThisPunct = false;
            } else {
                final Lexeme lastInStack = stack.getLast();
                System.out.println("lastInStack:" + lastInStack);
                final Integer stackFirstElemPrior = lexemePriorities.get(lastInStack.getValue());
                final Integer currOperPrior = lexemePriorities.get(lexeme.getValue());

                System.out.println("stackFirstElemPrior " + stackFirstElemPrior);
                System.out.println("currOperPrior " + currOperPrior);
                //stackFirstElemPrior is null for GOTOS
                if (stackFirstElemPrior == null) {
                    stack.add(lexeme);
                    shouldRepeatThisPunct = false;
                } else if (stackFirstElemPrior >= currOperPrior) {
                    exit.add(stack.removeLast());
                    shouldRepeatThisPunct = true;
                } else {
                    stack.add(lexeme);
                    shouldRepeatThisPunct = false;
                }
            }
        } while (shouldRepeatThisPunct);
    }

    private static String formatExit(LinkedList<Lexeme> exit) {
        return exit.stream().map(Lexeme::getValue)
            .collect(Collectors.joining(","));
    }


}
