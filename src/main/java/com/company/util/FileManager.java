package com.company.util;

import com.company.data.Library;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    private final Path fileLocationPath;

    public FileManager(Path fileLocationPath) {
        this.fileLocationPath = fileLocationPath;
    }

    public void writeLibraryToFile(Library library) {
        String saveFileName = "LibrarySave";
        Path savePath = fileExists(fileLocationPath, saveFileName);
        try {
            if (!Files.isDirectory(fileLocationPath.getParent())) {
                Files.createDirectory(fileLocationPath.getParent());
            }
            Files.createFile(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(savePath.toFile());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(library);
            System.out.println("Data was saved to file " + savePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("An error occurred when application was trying to save file: " + savePath.getFileName());
        }
    }

    public Library readLibraryFromFile() {
        Library library = null;

        try (FileInputStream fileInputStream = new FileInputStream(fileLocationPath.toString());
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            library = (Library) objectInputStream.readObject();

        } catch (FileNotFoundException e) {
            System.err.println("File was not found at filepath: " + fileLocationPath.toAbsolutePath().toString());
        } catch (IOException e) {
            System.err.println("An error occurred when application was trying to read file: " + fileLocationPath.getFileName());
        } catch (ClassNotFoundException e) {
            System.err.println("File " + fileLocationPath.getFileName() + " is not a Library serialization class file");
        }
        return library;
    }

    private Path fileExists(Path path, String saveFileName) {
        int count = 0;
        String suffix = ".ser";
        path = path.resolveSibling(saveFileName + suffix);
        while (path.toFile().exists()) {
            path = path.resolveSibling(saveFileName + count + suffix);
            count++;
        }
        if (count > 5)
            System.out.println("Consider removing some old LibrarySave.ser files");
        return path;
    }
}
