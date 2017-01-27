package com.company.app;

import com.company.data.*;
import com.company.util.DataReader;
import com.company.util.FileManager;
import com.company.util.LibraryUtilities;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class AppLogic {

    private Library library;
    private FileManager fileManager;
    private DataReader dataReader;
    private boolean improperExit;

    public AppLogic(String fileLocation) {
        if (fileLocation == null) {
            library = new Library();
            fileManager = new FileManager(Paths.get(System.getProperty("user.home"), "LibraryApp", "Library.ser"));
            System.out.println("File path not specified. New Library object was created, please save library.");
        } else {
            fileManager = new FileManager(Paths.get(fileLocation));
            library = fileManager.readLibraryFromFile();
            if (library == null) {
                library = new Library();
                System.out.println("New Library object was created, please save library.");
            } else {
                System.out.println("Library file has been read.");
            }
        }
        dataReader = new DataReader();
    }

    public void start() {
        if(improperExit == true) {
            System.out.println("Improper exit. Please close app with option [0] to save the data.");
        }
        improperExit = true;
        Option option = null;
        while (option != Option.EXIT) {
            try {
                printOptions();
                option = Option.createOptionThroughUserInput(dataReader.getInput());
                switch (option) {
                    case PRINT_LIBRARY_USERS:
                        printLibraryUsers();
                        break;
                    case BORROW_PUBLICATION_TO_USER:
                        borrowAPublicationToTheUser();
                        break;
                    case CLAIM_PUBLICATION_FROM_USER:
                        removeAPublicationFromUserBorrowedPublications();
                        break;
                    case ADD_MAGAZINE:
                        addMagazine();
                        break;
                    case PRINT_MAGAZINES:
                        printMagazines();
                        break;
                    case ADD_BOOK:
                        addBook();
                        break;
                    case PRINT_BOOKS:
                        printBooks();
                        break;
                    case ADD_USER:
                        addUser();
                        break;
                    case REMOVE_USER:
                        removeUser();
                        break;
                    case EXIT:
                        exit();
                        improperExit = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Data you inserted is not valid, please try again");
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("Chosen option doesn't exist.");
            }
        }
        dataReader.closeScanner();
    }

    private void printOptions() {
        System.out.println("Choose option: ");
        for (Option o : Option.values()) {
            System.out.println(o);
        }
    }

    private void printLibraryUsers() {
        LibraryUtilities.printSortedLibraryUsers(library, LibraryUtilities.sortUsersByLastThenFirstName);
    }

    private void borrowAPublicationToTheUser() {
        int publicationId = dataReader.readInputAndGetId("Insert publication id to borrow it: ");
        int userId = dataReader.readInputAndGetId("Insert user id: ");
        if (publicationId > library.getLibraryUsers().size() || userId > library.getPublications().size())
            throw new InputMismatchException("One of the ids is greater than the highest id number");
        LibraryUser libraryUser = library.getLibraryUsers().get(userId);
        Publication publication = library.getPublications().get(publicationId);

        library.addAPublicationToUserBorrowedPublications(libraryUser, publication);
    }

    private void removeAPublicationFromUserBorrowedPublications() {
        int publicationId = dataReader.readInputAndGetId("Insert publication id to reclaim it: ");
        int userId = dataReader.readInputAndGetId("Insert user id: ");
        if (publicationId > library.getLibraryUsers().size() || userId > library.getPublications().size())
            throw new InputMismatchException("One of the ids is greater than the highest id number");
        LibraryUser libraryUser = library.getLibraryUsers().get(userId);
        Publication publication = library.getPublications().get(publicationId);

        library.removeAPublicationFromUserBorrowedPublications(libraryUser, publication);
    }



    private void addMagazine() {
        library.addPublication(dataReader.readInputAndCreateMagazine());
    }

    private void printMagazines() {
        LibraryUtilities.printConcretePublicationsSortedByYears(library, Magazine.class);
    }

    private void addBook() {
        library.addPublication(dataReader.readInputAndCreateBook());
    }

    private void printBooks() {
        LibraryUtilities.printConcretePublicationsSortedByYears(library, Book.class);
    }

    private void addUser() {
        library.addUser(dataReader.readInputAndCreateLibraryUser());
    }

    private void removeUser() {
        int userId = dataReader.readInputAndGetId("To remove user insert user id: ");
        library.getLibraryUsers().remove(userId);
    }

    private void exit() {
        fileManager.writeLibraryToFile(library);
    }

    private enum Option {
        EXIT(0, "Exit program"),
        PRINT_LIBRARY_USERS(1, "Print library users"),
        BORROW_PUBLICATION_TO_USER(2, "Add book to user's book list"),
        CLAIM_PUBLICATION_FROM_USER(3, "Claim book from user"),
        ADD_MAGAZINE(4, "Add magazine"),
        PRINT_MAGAZINES(5, "Print all magazines"),
        ADD_BOOK(6, "Add book"),
        PRINT_BOOKS(7, "Print all books"),
        ADD_USER(8, "Add user"),
        REMOVE_USER(9, "Remove user");

        private int userInput;
        private String description;

        Option(int userInput, String description) {
            this.userInput = userInput;
            this.description = description;
        }

        @Override
        public String toString() {
            return userInput + " - " + description;
        }

        public static Option createOptionThroughUserInput(int userInput) throws NoSuchElementException {
            Option result;
            try {
                result = Option.values()[userInput];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException("Entered number is not represent by option.");
            }

            return result;
        }
    }
}
