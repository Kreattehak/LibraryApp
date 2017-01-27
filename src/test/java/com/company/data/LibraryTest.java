package com.company.data;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class LibraryTest {

    private Library library;
    private LibraryUser libraryUser = new LibraryUser("Jan", "Nowak", "94061245789");
    private Book book =  new Book(LocalDate.of(1992, 1, 1), "Book number 1", "PDW", "Deny Dev", 654, "156", "Hardcover");

    @Before
    public void setUp() throws Exception {
        library = new Library();
    }

    @After
    public void tearDown() throws Exception {
        library = null;
    }

    @Test
    public void shouldAddPublication() {
        library.addPublication(book);
        assertThat("Publication map doesn't contain key for book with id = 0", library.getPublications(),
                Matchers.hasKey(book.getPublicationId()));
        assertThat("Publication map doesn't contain book object", library.getPublications(), Matchers.hasValue(book));
    }

    @Test
    public void shouldRemovePublication() {
        shouldAddPublication();

        library.removePublication(book);
        assertFalse("Map still has key to book object.", library.getPublications().containsKey(book.getPublicationId()));
        assertFalse("Map still has book object.", library.getPublications().containsValue(book));
        assertThat("Map size isn't 0.", library.getPublications().size(), Matchers.is(0));
    }

    @Test
    public void shouldAddUser() {
        library.addUser(libraryUser);
        assertThat("User id is not present amongst integerToLibraryUserMap keys", library.getLibraryUsers(),
                Matchers.hasKey(libraryUser.getUserId()));
        assertThat("User is not present in integerToLibraryUserMap, that contains all library users",
                library.getLibraryUsers(), Matchers.hasValue(libraryUser));
    }

    @Test
    public void shouldRemoveUser() {
        shouldAddUser();

        library.removeUser(libraryUser);
        assertFalse("Map still has key to book object.", library.getLibraryUsers().containsKey(libraryUser.getUserId()));
        assertFalse("Map still has book object.", library.getLibraryUsers().containsValue(libraryUser));
        assertThat("Map size isn't 0.", library.getLibraryUsers().size(), Matchers.is(0));
    }

}