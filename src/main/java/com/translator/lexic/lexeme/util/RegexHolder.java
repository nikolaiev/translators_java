package com.translator.lexic.lexeme.util;

public class RegexHolder {

    public static final  String  IDENTIFIER_REGEX = "[a-zA-Z]{1}[a-zA-Z0-9#]*|#[a-zA-Z]{1,}";
    public static final  String UNSIGNED_NUMBER_REGEX = "[0-9]{1,}\\.?[0-9]*";
    public static final  String STRING_REGEX = "\\'.*\\'";

    private RegexHolder(){
        //utility class
    }
}
