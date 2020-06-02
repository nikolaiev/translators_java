package com.translator.lexic.poliz;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiFunction;

import static java.lang.Float.parseFloat;

@Slf4j
public class PolizInterpreter {

    static HashMap<Lexeme, String> identifiersValues;
    static HashMap<Lexeme, String> IdentifiersAndNumericLexemTypes;

    static LinkedList<Lexeme> stack;

    public static void interpret(LinkedList<Lexeme> polizLexemes) {
        identifiersValues = new HashMap<>();
        IdentifiersAndNumericLexemTypes = PolizConverter.identifiersTypes; //статика от которой надо бы отказываться
        stack = new LinkedList<>();

        for (int step = 0; step < polizLexemes.size(); step++) {
            System.out.println("Stack " + stack);
            final Lexeme lexeme = polizLexemes.get(step);
            System.out.println("Next lexeme " + lexeme);
            if (lexeme.getType() == LexemeType.IDENTIFIER
                || lexeme.getType() == LexemeType.NUMBER
                || lexeme.getType() == LexemeType.STRING
            ) {
                stack.add(lexeme);
            } else if (lexeme.getValue().equals("@")) {//унарний мінус -- //TODO || lexeme.getType() == LexemeType.KEYWORD true
                унарнийМінус();
            } else if (lexeme.getValue().equals("=")) {
                операторПрисвоєння();
            } else if (lexeme.getValue().equals("+")) { //бінарні оператори
                бінарнийОператор(Float::sum);
            } else if (lexeme.getValue().equals("-")) {
                бінарнийОператор((a1, a2) -> a1 - a2);
            } else if (lexeme.getValue().equals("*")) {
                бінарнийОператор((a1, a2) -> a1 * a2);
            } else if (lexeme.getValue().equals("/")) {
                бінарнийОператор((a1, a2) -> a1 / a2);
            } else if (lexeme.getValue().equals("^")) {
                бінарнийОператор((a1, a2) -> (float) Math.pow(a1, a2));
            } else if (lexeme.getValue().equals("%")) {
                бінарнийОператор((a1, a2) -> a1 % a2);
            }
        }

        System.out.println("Stack " + stack);
        System.out.println("Identifiers " + identifiersValues);
    }

    private static void операторПрисвоєння() {
        final Lexeme value = stack.removeLast(); //значення
        final Lexeme identifier = stack.removeLast(); //ідентифікатор

        if (identifier.getType() != LexemeType.IDENTIFIER) {
            throw new RuntimeException("Illegal operation. Can only assign value to identifier. Error at: " + identifier);
        }
        final String identifierType = IdentifiersAndNumericLexemTypes.get(identifier);
        if (identifierType == null) {
            throw new RuntimeException("Undeclared variable. " + identifier);
        }
        String valueType = IdentifiersAndNumericLexemTypes.get(value);
        if (valueType == null && value.getType() == LexemeType.NUMBER) {
            valueType = value.getValue().matches("-?((\\d+\\.\\d*)|(\\.\\d+))") ? "float" : "int";

        }
        if (valueType.equals("float") && !identifierType.equals("float")) {
            throw new RuntimeException("Illegal operation. Can only assign float to float type var. Error at: " + identifier);
        }
        if (valueType.equals("string") && !identifierType.equals("string")) {
            throw new RuntimeException("Illegal operation. Can only assign string to string type var. Error at: " + identifier);
        }

        identifiersValues.put(identifier, value.getValue());
        //identifiersTypes.put(identifier, identifiersTypes.get(value));
    }

    private static void бінарнийОператор(BiFunction<Float, Float, Float> action) {
        final Lexeme rightLexeme = stack.removeLast();
        final Lexeme leftLexeme = stack.removeLast();
        System.out.println("rightLexeme:" + rightLexeme);
        System.out.println("leftLexeme:" + leftLexeme);

        String rightVarValue = getVarValueAsString(rightLexeme);
        String leftVarValue = getVarValueAsString(leftLexeme);

        String rightVarType = IdentifiersAndNumericLexemTypes.get(rightLexeme);
        String leftVarType = IdentifiersAndNumericLexemTypes.get(leftLexeme);
        String resulType = !"float".equals(leftVarType) && !"float".equals(rightVarType) ? "int" : "float";

        float result = action.apply(parseFloat(leftVarValue), parseFloat(rightVarValue));

        Lexeme resultLexeme = Lexeme.of(String.valueOf(result), LexemeType.NUMBER);
        IdentifiersAndNumericLexemTypes.put(resultLexeme, resulType);
        stack.add(resultLexeme);
    }

    private static void унарнийМінус() {
        Lexeme stackTopLexem = stack.removeLast();
        String varValue = getVarValueAsString(stackTopLexem);
        String type = IdentifiersAndNumericLexemTypes.get(stackTopLexem);
        float initialValue = parseFloat(varValue);

        Lexeme result = Lexeme.of(String.valueOf(-initialValue), LexemeType.NUMBER);
        IdentifiersAndNumericLexemTypes.put(result, type);
        stack.add(result);
    }

    private static String getVarValueAsString(Lexeme stackTopLexem) {
        String varValue = identifiersValues.get(stackTopLexem);
        if (stackTopLexem.getType() == LexemeType.IDENTIFIER
            && varValue == null) {
            throw new RuntimeException("Uninitialized var found." + stackTopLexem);
        }
        if (stackTopLexem.getType() == LexemeType.NUMBER) {
            varValue = stackTopLexem.getValue();
        }
        return varValue;
    }
}
