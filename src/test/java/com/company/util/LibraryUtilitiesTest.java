package com.company.util;

import com.company.data.Book;
import com.company.data.Library;
import com.company.data.LibraryUser;
import com.company.data.Publication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class LibraryUtilitiesTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final Pattern pattern = Pattern.compile(", amount of borrowed books: |\\R");

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void shouldPrintUsersSortedByLastThenFirstName() {
        String expectedOutput = "Jan Bobko2Andrzej Kowalski3Daniel Nowak4Jan Nowak2Jan Nowak3Adrian Pomykała0" +
                "Agnieszka Szewczyk3Janusz Tydniarski1";

        Library libraryStub = Mockito.mock(Library.class);
        Mockito.when(libraryStub.getLibraryUsers()).thenReturn(fakeLibraryUsers());

        LibraryUtilities.printSortedLibraryUsers(libraryStub, LibraryUtilities.sortUsersByLastThenFirstName);
        assertEquals("Print information sorted by last then first name failed.",
                expectedOutput, cutUnnecessaryCharacters(outContent));
    }

    @Test
    public void shouldPrintUsersSortedByQuantityOfBorrowedPublications() {
        String expectedOutput = ("Daniel Nowak4Andrzej Kowalski3Agnieszka Szewczyk3Jan Nowak3Jan Nowak2" +
                "Jan Bobko2Janusz Tydniarski1Adrian Pomykała0");

        Library libraryStub = Mockito.mock(Library.class);
        Mockito.when(libraryStub.getLibraryUsers()).thenReturn(fakeLibraryUsers());

        LibraryUtilities.printSortedLibraryUsers(libraryStub, LibraryUtilities.sortUsersByAmountOfBorrowedPublications);
        assertEquals("Print information sorted by amount of borrowed publications failed.",
                expectedOutput, cutUnnecessaryCharacters(outContent));
    }

    @Test
    public void shouldPrintUsersSortedByTheirId() {
        String expectedOutput = "Jan Nowak2Daniel Nowak4Andrzej Kowalski3Agnieszka Szewczyk3Jan Bobko2" +
                "Janusz Tydniarski1Adrian Pomykała0Jan Nowak3";

        Library libraryStub = Mockito.mock(Library.class);
        Mockito.when(libraryStub.getLibraryUsers()).thenReturn(fakeLibraryUsers());

        LibraryUtilities.printSortedLibraryUsers(libraryStub, LibraryUtilities.sortUsersById);
        assertEquals("Print information sorted by user id failed.",
                expectedOutput, cutUnnecessaryCharacters(outContent));
    }

    @Test
    public void shouldPrintBooksSortedByYear() {
        String expectedOutput = "1754 Book number 2 ORK Deny Dev 10441992 Book number 1 PDW Deny Dev 654" +
                "2008 Book number 4 All Okal Kaiql 2442016 Book number 3 ZBR Deny Dev 742";

        Library libraryStub = Mockito.mock(Library.class);
        Mockito.when(libraryStub.getPublications()).thenReturn(fakePublications());

        LibraryUtilities.printConcretePublicationsSortedByYears(libraryStub, Book.class);
        assertEquals("Print publications by type of subclass and sorted by year of release failed.",
                expectedOutput, cutUnnecessaryCharacters(outContent));
    }

    @Test
    public void shouldPrintRightAmountOfBooks() {
        Map<Integer, Publication> publications = fakePublications();
        Library libraryStub = Mockito.mock(Library.class);
        Mockito.when(libraryStub.getPublications()).thenReturn(fakePublications());

        long amount = LibraryUtilities.getAmountOfConcretePublications(libraryStub, Book.class);
        assertEquals("Map size isn't equal to proper value",
                publications.size(), amount);
    }









    private Map<Integer, LibraryUser> fakeLibraryUsers() {
        LibraryUser[] libraryUsers = {
                new LibraryUser("Jan", "Nowak", "94061245789"),
                new LibraryUser("Daniel", "Nowak", "58110325987"),
                new LibraryUser("Andrzej", "Kowalski", "46112305839"),
                new LibraryUser("Agnieszka", "Szewczyk", "95091119508"),
                new LibraryUser("Jan", "Bobko", "35112609319"),
                new LibraryUser("Janusz", "Tydniarski", "89111911678"),
                new LibraryUser("Adrian", "Pomykała", "39110610320"),
                new LibraryUser("Jan", "Nowak", "68032900738")
        };

        Map<Integer, LibraryUser> libraryUsersInMemory = new HashMap<>();
        for (int i = 0; i < libraryUsers.length; i++) {
            libraryUsersInMemory.put(i, libraryUsers[i]);
        }

        List<Publication> listOfBooks = new ArrayList<>(fakePublications().values());

        int[] amountOfBorrowedBooks = {2, 4, 3, 3, 2, 1, 0, 3, 5, 3};
        int counter = 0;

        for (Map.Entry<Integer, LibraryUser> libraryUser : libraryUsersInMemory.entrySet()) {
            for (int i = 0; i < amountOfBorrowedBooks[counter]; i++) {
                libraryUser.getValue().getBorrowedPublications().add(listOfBooks.get(i));
            }
                counter++;
        }

        return libraryUsersInMemory;
    }

    private Map<Integer, Publication> fakePublications() {
        Publication[] publications = {
                new Book(LocalDate.of(1992, 1, 1), "Book number 1", "PDW", "Deny Dev", 654, "156", "Hardcover"),
                new Book(LocalDate.of(2008, 1, 1), "Book number 4", "All", "Okal Kaiql", 244, "123", "Paperback"),
                new Book(LocalDate.of(1754, 1, 1), "Book number 2", "ORK", "Deny Dev", 1044, "793", "PAPERBACK"),
                new Book(LocalDate.of(2016, 1, 1), "Book number 3", "ZBR", "Deny Dev", 742, "478", "paperback"),
        };
        Map<Integer, Publication> integerToPublicationMap = new HashMap<>();
        for (int i = 0; i < publications.length; i++) {
            integerToPublicationMap.putIfAbsent(publications[i].getPublicationId(), publications[i]);
        }
        return integerToPublicationMap;
    }

    private String cutUnnecessaryCharacters(ByteArrayOutputStream outContent) {
        String consoleOutput = outContent.toString();

        Matcher matcher = pattern.matcher(consoleOutput);

        if(matcher.find())
            return matcher.replaceAll("");
        else
            return "Matcher found none matches";
    }
}