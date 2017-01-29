package com.company.app;

import com.company.util.PathRecognizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainApp {
    public static void main(String[] args) {
        String fileLocation = null;
        try {
            Files.readAllLines(Paths.get("src/main/resources/intro.txt"))
                    .stream()
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (args.length == 1) {
            fileLocation = PathRecognizer.recognizePath(args[0]);
        }

        AppLogic appLogic = new AppLogic(fileLocation);
        appLogic.start();
    }
}
