package com.company.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathRecognizer {

    public static String recognizePath(String path) {
        Pattern pattern = Pattern.compile("^(.+)(/|\\\\)([^(/|\\\\)]+$)");
        Matcher matcher = pattern.matcher(path);
        if (!matcher.matches())
            throw new IllegalArgumentException("Path was not recognized. Please specify your path!");
        System.out.println("User passed path to file: " + matcher.group());
        return path;
    }
}
