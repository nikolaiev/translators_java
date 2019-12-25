package com.translator.lexic.syntax.magazine;


import static java.lang.String.format;

public class Warning {

    private int lineNumber;
    private String content;


    Warning(int lineNumber, String content) {
        this.lineNumber = lineNumber + 1;
        this.content = content;
    }

    @Override
    public String toString() {
        return format("Unexpected symbol at %s line %s\n", lineNumber, content);
    }
}
