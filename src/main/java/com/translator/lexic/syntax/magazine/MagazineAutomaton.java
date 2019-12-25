package com.translator.lexic.syntax.magazine;


import com.translator.lexic.lexeme.model.Lexeme;
import com.translator.lexic.lexeme.model.LexemeType;
import com.translator.lexic.syntax.SyntaxAnalyzer;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import static com.translator.lexic.syntax.magazine.AutomatonStateConfiguration.AUTOMATON_STATES;

@Data
public class MagazineAutomaton implements SyntaxAnalyzer {

    private List<Warning> warnings;
    private ListIterator<Lexeme> lexemeIterator;
    private LinkedList<Integer> stack;

    public MagazineAutomaton(List<Lexeme> lexems) {
        List<Lexeme> lexemes = lexems.stream().filter(l -> l.getType() != LexemeType.WHITE).collect(Collectors.toList());
        lexemeIterator = lexemes.listIterator();
        stack = new LinkedList<>();
        stack.addFirst(-1);
        warnings = new ArrayList<>();
    }

    @Override
    public void analyze() {
        ListIterator<AutomatonState> mainIterator = AUTOMATON_STATES.listIterator();
        Lexeme lexeme = getNextInputLexeme();
        AutomatonState state = mainIterator.next();

        while (true) {
            int lexemeCode = lexeme.getCode();
            int stateNumber = state.getNumber();
            //якщо стани з декількома варіантами переходу
            if (stateHasSeveralOptions(stateNumber)) {
                while (!state.isExitAutomatonFlag()  // якщо не стан закінчення роботи автоматору
                    && lexemeCode != getLexemeCode(state)) {
                    if (mainIterator.hasNext()) {
                        state = mainIterator.next();
                        //коли пройшли всі варіанти перебору але жоден не підійшов
                        if (state.getNumber() != stateNumber && !state.isExitAutomatonFlag()) {
                            warnings.add(new Warning(lexeme.getLineIndex(), lexeme.getValue()));
                            return;
                        }
                    } else {
                        warnings.add(new Warning(lexeme.getLineIndex(), lexeme.getValue()));
                        return;
                    }
                }
                //всі інші стани
            } else if (lexemeCode != getLexemeCode(state) && !state.isExitAutomatonFlag()) {
                warnings.add(new Warning(lexeme.getLineIndex(), lexeme.getValue()));
                return;
            }


            //видаляємо зі стеку, якщо на вершині стеку
            if (stack.getFirst().equals(state.getNumber())) {
                //System.out.println("Remove from stack " + state.getNumber());
                stack.removeFirst();
            }
            //додаємо в стек, якщо необхідно
            if (state.getStack() != -1) {
                //System.out.println("Add to stack " + state.getStack());
                stack.push(state.getStack());
            }


            //якщо стан має вказане значення для додавання в стек - додаємо і запускаємо підавтомат
            if (state.getNextNumber() == -2) {
                mainIterator = setStateByNumber(stack.getFirst());
            } else if (state.getNextNumber() == -1) { //признак закінчення аналізу
                Lexeme nextInputLexeme = getNextInputLexeme();
                if (nextInputLexeme != null) {
                    warnings.add(new Warning(nextInputLexeme.getLineIndex(), nextInputLexeme.getValue()));
                }
                return;
            } else {
                mainIterator = setStateByNumber(state.getNextNumber());
            }

            if (!state.isExitAutomatonFlag()) {
                lexeme = getNextInputLexeme();
            }
            //System.out.println("\nprev state " + state.getNumber());
            state = mainIterator.next();
            //System.out.println("new state " + state.getNumber());
            //System.out.println("new lexem " + lexeme);

        }
    }

    private boolean stateHasSeveralOptions(int stateNumber) {
        return AUTOMATON_STATES.stream().filter(a -> a.getNumber() == stateNumber).count() > 1;
    }

    private int getLexemeCode(AutomatonState state) {
        if (state.getLexeme() != null) {
            return state.getLexeme().getCode();
        }
        return -999;
    }

    private Lexeme getNextInputLexeme() {
        if (lexemeIterator.hasNext()) {
            return lexemeIterator.next();
        }
        return null;
    }

    private ListIterator<AutomatonState> setStateByNumber(int number) {
        ListIterator<AutomatonState> iterator = AUTOMATON_STATES.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getNumber() == number) {
                if (iterator.hasPrevious()) {
                    iterator.previous();
                }
                return iterator;
            }
        }
        return null;
    }
}
