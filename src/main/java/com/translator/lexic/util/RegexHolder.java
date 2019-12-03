package com.translator.lexic.util;

public class RegexHolder {

    public static final  String  IDENTIFIER_REGEX = "[a-zA-Z#]{1}[a-zA-Z0-9#]*";
    public static final  String UNSIGNED_NUMBER_REGEX = "[0-9]*\\.?[0-9]*";
    public static final  String STRING_REGEX = "\\'.*\\'";

    private RegexHolder(){
        //utility class
    }
}
