package com.translator.lexic.syntax.exception;

import java.util.NoSuchElementException;

public class SyntaxAnalyzerException extends RuntimeException {

    public SyntaxAnalyzerException(String cause){
        super(cause);
    }

    public SyntaxAnalyzerException(String cause, NoSuchElementException ignored) {
        super(cause,ignored);
    }
}