package com.translator.lexic.poliz;

import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiFunction;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;

@Slf4j
public class PolizInterpreter {

    private static final String FLOAT_REGEX = "-?((\\d+\\.\\d*)|(\\.\\d+))";

    static HashMap<Lexeme, String> identifiersValues;
    static HashMap<Lexeme, String> identifiersAndNumericLexemTypes;

    static LinkedList<Lexeme> stack;

    public static void interpret(LinkedList<Lexeme> polizLexemes) {
        identifiersValues = new HashMap<>();
        identifiersAndNumericLexemTypes = PolizConverter.identifiersTypes; //статика от которой надо бы отказываться
        stack = new LinkedList<>();

        for (int step = 0; step < polizLexemes.size(); step++) {
            //System.out.println("\nStack " + stack);
            final Lexeme lexeme = polizLexemes.get(step);
            //System.out.println("Next lexeme " + lexeme);
            if (lexeme.getType() == LexemeType.IDENTIFIER
                || lexeme.getType() == LexemeType.NUMBER
                || lexeme.getType() == LexemeType.STRING
                || lexeme.getType() == LexemeType.BOOL
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
            } else if (lexeme.getValue().equals("in")) {//вивід ввод
                операторВводу();
            } else if (lexeme.getValue().equals("out")) {
                операторВиводу();
            } else if (lexeme.getType() == LexemeType.REL_OP) {
                операціяВідношення(lexeme);
            } else if (lexeme.getType() == LexemeType.KEYWORD) {//з цим типом маются в полізі тільки мітки
                final String _gotoName = lexeme.getValue();
                if (step == polizLexemes.size() - 1) {
                    //noop end of program
                } else {
                    final Lexeme nextLexeme = polizLexemes.get(step + 1);
                    if (nextLexeme.getValue().equals("УПХ")) {//else noop
                        final Lexeme boolLexeme = stack.removeLast();
                        if (boolLexeme.getType() != LexemeType.BOOL) {
                            throw new RuntimeException("Something wrong with УПХ");
                        }
                        final boolean condition = Boolean.parseBoolean(boolLexeme.getValue());
                        if (!condition) { //УПХ - умова переходу на ХИБУ
                            step = searchStepForGoto(polizLexemes, _gotoName, step);
                        }// else noop
                    }// else noop
                }
            }
        }

        //System.out.println("Stack " + stack);
        //System.out.println("Identifiers " + identifiersValues);
        //System.out.println("IdentifiersTypes " + identifiersAndNumericLexemTypes);
    }

    private static int searchStepForGoto(LinkedList<Lexeme> polizLexemes, String gotoName, int step) {
        for (int index = 0; index < polizLexemes.size(); index++) {
            if (polizLexemes.get(index).getValue().equals(gotoName) && index != step) {//i!=step - need to find another GOTO ratehr when GOTO that has triggered this search
                return index;
            }
        }

        throw new RuntimeException("Something wrong with УПХ next GOTO search");
    }

    private static void операціяВідношення(Lexeme lexeme) {
        final String lexemeValue = lexeme.getValue();
        if (lexemeValue.equals("!")) { //unary operator
            унарнеЗаперечення();
        } else if (lexemeValue.equals(">")) { //binary relational operator
            бінарнийОператорВідношення((a1, a2) -> a1 > a2);
        } else if (lexemeValue.equals(">=")) {
            бінарнийОператорВідношення((a1, a2) -> a1 >= a2);
        } else if (lexemeValue.equals("<")) {
            бінарнийОператорВідношення((a1, a2) -> a1 < a2);
        } else if (lexemeValue.equals("<=")) {
            бінарнийОператорВідношення((a1, a2) -> a1 <= a2);
        } else if (lexemeValue.equals("==")) {
            бінарнийОператорВідношення((a1, a2) -> a1.floatValue() == a2.floatValue());
        } else if (lexemeValue.equals("!=")) {
            бінарнийОператорВідношення((a1, a2) -> a1.floatValue() != a2.floatValue());
        } else if (lexemeValue.equals("&&")) {
            бінарнийОператорКонюнкції((a1, a2) -> a1 && a2);
        } else if (lexemeValue.equals("||")) {
            бінарнийОператорКонюнкції((a1, a2) -> a1 || a2);
        }

    }

    private static void бінарнийОператорКонюнкції(BiFunction<Boolean, Boolean, Boolean> action) {
        final Lexeme rightLexeme = stack.removeLast();
        final Lexeme leftLexeme = stack.removeLast();
        //System.out.println("rightLexeme:" + rightLexeme);
        //System.out.println("leftLexeme:" + leftLexeme);

        String rightVarValue = getVarValueAsString(rightLexeme);
        String leftVarValue = getVarValueAsString(leftLexeme);

        boolean result = action.apply(parseBoolean(leftVarValue), parseBoolean(rightVarValue));

        Lexeme resultLexeme = Lexeme.of(String.valueOf(result), LexemeType.BOOL);
        identifiersAndNumericLexemTypes.put(resultLexeme, "bool");
        stack.add(resultLexeme);
    }

    private static void бінарнийОператорВідношення(BiFunction<Float, Float, Boolean> action) {
        final Lexeme rightLexeme = stack.removeLast();
        final Lexeme leftLexeme = stack.removeLast();
        //System.out.println("rightLexeme:" + rightLexeme);
        //System.out.println("leftLexeme:" + leftLexeme);

        String rightVarValue = getVarValueAsString(rightLexeme);
        String leftVarValue = getVarValueAsString(leftLexeme);

        boolean result = action.apply(parseFloat(leftVarValue), parseFloat(rightVarValue));

        Lexeme resultLexeme = Lexeme.of(String.valueOf(result), LexemeType.BOOL);
        identifiersAndNumericLexemTypes.put(resultLexeme, "bool");
        stack.add(resultLexeme);
    }

    private static void унарнеЗаперечення() {
        Lexeme stackTopLexem = stack.removeLast();
        String varValue = getVarValueAsString(stackTopLexem);
        String type = identifiersAndNumericLexemTypes.get(stackTopLexem);
        boolean initialValue = parseBoolean(varValue);

        Lexeme result = Lexeme.of(String.valueOf(!initialValue), LexemeType.BOOL);
        identifiersAndNumericLexemTypes.put(result, type);
        stack.add(result);
    }

    private static void операторВиводу() {
        Lexeme value = stack.removeLast(); //значення
        while (value.getType() == LexemeType.IDENTIFIER
            || value.getType() == LexemeType.NUMBER
            || value.getType() == LexemeType.STRING
            || value.getType() == LexemeType.BOOL) {
            LexemeType valueType = value.getType();
            //log.debug("here: {}",value);
            if (valueType == LexemeType.IDENTIFIER) {
                final String valueStr = identifiersValues.get(value);
                if (valueStr == null) {
                    throw new RuntimeException("Trying to print uninitialized var: " + valueStr);
                }
                System.out.println(valueStr);
            } else {
                //костиль, так як лексема строки додається з кавичками ' .ТОДО
                System.out.println(value.getValue().replaceAll("'", ""));
            }
            if (stack.isEmpty()) {
                break;
            }
            value = stack.removeLast(); //наступне значення
        }
    }

    private static void операторВводу() {
        try {
            //since stack is reversed we need to re-reverse idens for IN operator
            LinkedList<Lexeme> inOperIdens = new LinkedList<>(); //simply reversed list if idens
            Lexeme lexeme = stack.removeLast(); //значення
            while (lexeme.getType() == LexemeType.IDENTIFIER) {
                inOperIdens.addFirst(lexeme);
                if (stack.isEmpty()) {
                    break;
                }
                lexeme = stack.removeLast(); //наступне значення
            }

            while (!inOperIdens.isEmpty()) {
                lexeme = inOperIdens.removeFirst(); //значення
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String valueRead = reader.readLine();

                final String identifierVarType = identifiersAndNumericLexemTypes.get(lexeme);

                if (identifierVarType.equals("float")) {
                    if (valueRead.matches(FLOAT_REGEX) || valueRead.matches("-?[0-9]+")) {
                        identifiersValues.put(lexeme, valueRead);
                    } else {
                        throw new RuntimeException("Invalid value passed for " + lexeme + " : " + valueRead + " . Float expected.");
                    }
                } else if (identifierVarType.equals("int")) {
                    if (valueRead.matches("-?[0-9]+")) {
                        identifiersValues.put(lexeme, valueRead);
                    } else {
                        throw new RuntimeException("Invalid value passed for " + lexeme + " : " + valueRead + " . Integer expected.");
                    }
                } else if (identifierVarType.equals("bool")) {
                    if (valueRead.matches("true|false")) {
                        identifiersValues.put(lexeme, valueRead);
                    } else {
                        throw new RuntimeException("Invalid value passed for " + lexeme + " : " + valueRead + " . Boolean expected.");
                    }
                } else if (identifierVarType.equals("string")) {
                    identifiersValues.put(lexeme, valueRead);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void операторПрисвоєння() {
        final Lexeme value = stack.removeLast(); //значення
        final Lexeme identifier = stack.removeLast(); //ідентифікатор

        if (identifier.getType() != LexemeType.IDENTIFIER) {
            throw new RuntimeException("Illegal operation. Can only assign value to identifier. Error at: " + identifier);
        }
        final String identifierType = identifiersAndNumericLexemTypes.get(identifier);
        if (identifierType == null) {
            throw new RuntimeException("Undeclared variable. " + identifier);
        }
        //get int float or bool or string
        String valueType = getValueType(value, identifier, identifierType);

        identifiersValues.put(identifier, value.getValue());
        identifiersAndNumericLexemTypes.put(identifier, valueType);
    }

    private static String getValueType(Lexeme value, Lexeme identifier, String identifierType) {
        //determine valueType
        String valueType = getLexemeValueType(value);

        if (valueType.equals("bool") && !identifierType.equals("bool")) {
            throw new RuntimeException("Illegal operation. Can only assign bool to bool type var. Error at: " + identifier);
        }
        if (valueType.equals("float") && !identifierType.equals("float")) {
            throw new RuntimeException("Illegal operation. Can only assign float to float type var. Error at: " + identifier);
        }
        if (valueType.equals("string") && !identifierType.equals("string")) {
            throw new RuntimeException("Illegal operation. Can only assign string to string type var. Error at: " + identifier);
        }
        return valueType;
    }

    private static String getLexemeValueType(Lexeme value) {
        String valueType = identifiersAndNumericLexemTypes.get(value);
        if (valueType == null && value.getType() == LexemeType.STRING) {
            valueType = "string";
        } else if (valueType == null && value.getType() == LexemeType.BOOL) {
            valueType = "bool";
        } else if (valueType == null && value.getType() == LexemeType.NUMBER) {
            valueType = value.getValue().matches(FLOAT_REGEX) ? "float" : "int";
        }
        return valueType;
    }

    private static void бінарнийОператор(BiFunction<Float, Float, Float> action) {
        final Lexeme rightLexeme = stack.removeLast();
        final Lexeme leftLexeme = stack.removeLast();
        //System.out.println("rightLexeme:" + rightLexeme);
        //System.out.println("leftLexeme:" + leftLexeme);

        String rightVarValue = getVarValueAsString(rightLexeme);
        String leftVarValue = getVarValueAsString(leftLexeme);

        String rightVarType = getLexemeValueType(rightLexeme);
        String leftVarType = getLexemeValueType(leftLexeme);

        String resulType = !"float".equals(leftVarType) && !"float".equals(rightVarType) ? "int" : "float";

        float result = action.apply(parseFloat(leftVarValue), parseFloat(rightVarValue));

        Lexeme resultLexeme = Lexeme.of(String.valueOf(result), LexemeType.NUMBER);
        identifiersAndNumericLexemTypes.put(resultLexeme, resulType);
        stack.add(resultLexeme);
    }

    private static void унарнийМінус() {
        Lexeme stackTopLexem = stack.removeLast();
        String varValue = getVarValueAsString(stackTopLexem);
        String type = getLexemeValueType(stackTopLexem);
        float initialValue = parseFloat(varValue);

        Lexeme result = Lexeme.of(String.valueOf(-initialValue), LexemeType.NUMBER);
        identifiersAndNumericLexemTypes.put(result, type);
        stack.add(result);
    }

    private static String getVarValueAsString(Lexeme stackTopLexem) {
        String varValue = identifiersValues.get(stackTopLexem);
        if (stackTopLexem.getType() == LexemeType.IDENTIFIER
            && varValue == null) {
            throw new RuntimeException("Uninitialized var found." + stackTopLexem);
        }
        if (stackTopLexem.getType() != LexemeType.IDENTIFIER) {
            varValue = stackTopLexem.getValue();
        }

        return varValue;
    }
}
