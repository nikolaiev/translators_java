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
        put(")", 1);
        put("||", 1);
        put("&&", 2);
        put("!", 3);

        put("<", 4); //20
        put("<=", 4); //21
        put("==", 4); //22
        put(">=", 4); //23
        put("!=", 4); //24
        put(">", 4); //26''

        put("+", 5);
        put("-", 5);
        put("@", 6); //УНАРНИЙ МІНУС
        put("*", 6);
        put("/", 6);

//        put("%", 5);
        put("^", 7);

    }};

    static Lexeme UNARY_MINUS = new Lexeme("@", LexemeType.KEYWORD);


    public static HashMap<String, String> getPoliz(LinkedList<Lexeme> polizLexemes) {
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

            if (lexeme.getValue().equals("-")) {
                //System.out.println("Minus detected");
                //System.out.println("Prev lexeme:" + previousLexeme);

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
            } else if (lexeme.getValue().equals("(")) {
                stack.add(lexeme);
            } else if (lexeme.getValue().equals(")")) {
                while (true) {
                    Lexeme lastOnStack = stack.removeLast();
                    if (lastOnStack.getValue().equals("(")) break;
                    exit.add(lastOnStack);
                }
            } else if (type == LexemeType.IDENTIFIER
                || type == LexemeType.STRING
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

        //TODO
        return null;
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

                if (stackFirstElemPrior >= currOperPrior) {
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
