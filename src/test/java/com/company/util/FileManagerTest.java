package com.company.util;

import com.company.data.Library;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FileManagerTest {

    private static Path path = Paths.get("src/test/java/com/company/util/noNameFile.ser");

    @AfterClass
    public static void cleanUpFiles() throws IOException {
        Files.delete(path.resolveSibling("LibrarySave.ser"));
    }

    @Test
    public void dataWrittenByFileMangerShouldBeDeserializable() {
        Library library = new Library();
        FileManager fileManager = new FileManager(path);
        fileManager.writeLibraryToFile(library);

        //new FileManager because we testing it's functionality in reversed order, proper order READ -> WRITE
        fileManager = new FileManager(Paths.get("src/test/java/com/company/util/LibrarySave.ser"));
        Library deserializedLibrary = fileManager.readLibraryFromFile();

        assertEquals("Libraries aren't equal", library, deserializedLibrary);
    }
}