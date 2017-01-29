package com.company.util;

import com.company.data.Book;
import com.company.data.LibraryUser;
import com.company.data.Magazine;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.InputMismatchException;

import static org.junit.Assert.*;

public class DataReaderTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private DataReader dataReader;

    @Test
    public void createMagazineFromUserInput() {
        String data = "12/06/2015\nTitle\nPublisher\nCategory";
        setSystemInForPreparedInput(data);
        Magazine standardMagazine = new Magazine(LocalDate.of(2015, 6, 12), "Title", "Publisher", "Category");
        Magazine magazine = dataReader.readInputAndCreateMagazine();
        assertEquals("Magazine created by DataReader is not equal to standard Magazine model.", standardMagazine, magazine);
    }

    @Test
    public void createBookFromUserInput() {
        String data = "2015\nTitle\nPublisher\nAuthor\n1054\n917-2-1234-5680-3\nHARDCOVER";
        setSystemInForPreparedInput(data);
        Book standardBook = new Book(LocalDate.of(2015, 1, 1), "Title", "Publisher", "Author", 1054,
                "9172123456803", "HARDCOVER");
        Book book = dataReader.readInputAndCreateBook();
        assertEquals("Book created by DataReader is not equal to standard Book model.", standardBook, book);
    }

    @Test
    public void createLibraryUserFromUserInput() {
        String data = "Kowalski\nJan\n98091161111";
        setSystemInForPreparedInput(data);
        LibraryUser standardLibraryUser = new LibraryUser("Kowalski", "Jan", "98091161111");
        LibraryUser libraryUser = dataReader.readInputAndCreateLibraryUser();
        assertEquals("Library user created by DataReader is not equal to standard LibraryUser model.",
                standardLibraryUser, libraryUser);
    }

    @Test
    public void shouldNotAcceptIsbnNotEqualTo10WhenYearOfPublicationIsLowerThan2007() {
        bookCreationValidation("2006\nTitle\nPublisher\nAuthor\n1054\n917-2-1234-5680-3\nHARDCOVER", "ISBN is not valid.");
    }

    @Test
    public void shouldNotAcceptIsbnNotEqualTo13WhenYearOfPublicationIsEqualOrGreaterThan2007() {
        bookCreationValidation("2007\nTitle\nPublisher\nAuthor\n1054\n2-1234-5680-3\nHARDCOVER", "ISBN is not valid.");
    }

    @Test
    public void givenCoverTypeShouldBeHardcoverOrPaperbackOnly() {
        bookCreationValidation("2007\nTitle\nPublisher\nAuthor\n1054\n917-2-1234-5680-3\nHard",
                "Cover type is not valid.");
    }

    @Test
    public void givenPersonalIdNumShouldNotBeLessThan11Digits() {
        personalIdNumValidation("Kowalski\nJan\n9809116111\n");
    }

    @Test
    public void givenPersonalIdNumShouldNotBeGreaterThan11Digits() {
        personalIdNumValidation("Kowalski\nJan\n980911611111\n");
    }

    @Test
    public void givenIdShouldNotBeLowerThan1() {
        setSystemInForPreparedInput("0");
        exception.expectMessage("Lowest id is 1.");
        exception.expect(InputMismatchException.class);
        dataReader.readInputAndGetId("Validating id: ");
    }

    private void personalIdNumValidation(String data) {
        setSystemInForPreparedInput(data);
        exception.expectMessage("Personal identity number is not valid.");
        exception.expect(InputMismatchException.class);
        dataReader.readInputAndCreateLibraryUser();
    }

    private void bookCreationValidation(String data, String exceptionMessage) {
        setSystemInForPreparedInput(data);
        exception.expectMessage(exceptionMessage);
        exception.expect(InputMismatchException.class);
        dataReader.readInputAndCreateBook();
    }

    private void setSystemInForPreparedInput(String data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
        System.setIn(in);
        dataReader = new DataReader();
    }

}