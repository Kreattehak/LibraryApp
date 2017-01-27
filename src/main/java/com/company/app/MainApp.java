package com.company.app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainApp {
    public static void main(String[] args) {
        String fileLocation = null;

        if (args.length == 1) {
            Pattern pattern = Pattern.compile("^(.+)(/|\\\\)([^(/|\\\\)]+$)");
            Matcher matcher = pattern.matcher(args[0]);
            if (!matcher.matches())
                throw new IllegalArgumentException("Path was not recognized. Please specify your path!");
            fileLocation = args[0];
            System.out.println("User passed path to file: " + matcher.group());
        }
        AppLogic appLogic = new AppLogic(fileLocation);
        appLogic.start();
    }
}
