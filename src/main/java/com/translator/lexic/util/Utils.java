package com.translator.lexic.util;

import com.translator.lexic.Application;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class Utils {

    public static String getFileContentAsString(String fileName) throws IOException {
        InputStream resourceAsStream = Application.class.getClassLoader().getResourceAsStream(fileName);
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
            (resourceAsStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        return textBuilder.toString();
    }
}
