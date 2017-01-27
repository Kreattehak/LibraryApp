package com.company.app;

import com.company.data.*;
import com.company.util.DataReader;
import com.company.util.FileManager;
import com.company.util.LibraryUtilities;

import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class AppLogic {

    private Library library;
    private FileManager fileManager;
    private DataReader dataReader;
    private LibraryUser libraryUser;
    private Publication publication;
    private boolean improperExit;

    private final int USER_MENU_MODIFIER = 2;
    private final int PUBLICATIONS_MENU_MODIFIER = 7;

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
        if (improperExit == true) {
            System.out.println("Improper exit. Please close app with option [0] to save the data.");
        }
        improperExit = true;
        Option option = null;
        while (option != Option.MAIN_MENU_EXIT) {
            try {
                System.out.println("Choose option: ");
                printOptions(0, 3);
                option = Option.createOptionThroughUserInput(dataReader.getInput());
                switch (option) {
                    case MAIN_MENU_EXIT:
                        exit();
                        break;
                    case MAIN_MENU_USERS:
                        printMainMenu(option);
                        break;
                    case MAIN_MENU_PUBLICATIONS:
                        printMainMenu(option);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Data you inserted is not valid, please try again.");
            } catch (NumberFormatException | NoSuchElementException e) {
                System.out.println("Chosen option doesn't exist. Redirecting to the main menu.");
            }
        }
        dataReader.closeScanner();
    }

    private void printOptions(int from, int to) {
        int optionNumber = 0;
        if (from != 0)
            optionNumber = 1;
        for (int i = from; i < to; i++) {
            System.out.println(optionNumber++ + " - " + Option.values()[i]);
        }
    }

    private void printMainMenu(Option option) {
        Option previousOption = option;
        Option currentMenuOption = option;
        mainLoop:
        while (currentMenuOption != Option.MAIN_MENU_EXIT) {
            System.out.println("========================");
            System.out.println("Now you are in " + previousOption.toString() + ". Choose option: ");
            System.out.println("0 - " + Option.MAIN_MENU_EXIT);
            if (currentMenuOption == Option.MAIN_MENU_USERS) {
                printOptions(3, 8);
                currentMenuOption = Option.createOptionThroughUserInput(dataReader.getInput() + USER_MENU_MODIFIER);
                switch (currentMenuOption) {
                    case PRINT_LIBRARY_USERS:
                        printLibraryUsers();
                        break;
                    case BORROW_PUBLICATION_TO_USER:
                        borrowAPublicationToTheUser();
                        break;
                    case CLAIM_PUBLICATION_FROM_USER:
                        removeAPublicationFromUserBorrowedPublications();
                        break;
                    case REMOVE_USER:
                        removeUser();
                        break;
                    case ADD_USER:
                        addUser();
                        break;
                    default:
                        break mainLoop;
                }
                currentMenuOption = Option.MAIN_MENU_USERS;
            } else if (currentMenuOption == Option.MAIN_MENU_PUBLICATIONS) {
                printOptions(8, 12);
                currentMenuOption = Option.createOptionThroughUserInput(dataReader.getInput() + PUBLICATIONS_MENU_MODIFIER);
                switch (currentMenuOption) {
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
                    default:
                        break mainLoop;
                }
                currentMenuOption = Option.MAIN_MENU_PUBLICATIONS;
            }
        }
    }

    private void exit() {
        fileManager.writeLibraryToFile(library);
        System.out.println("Have a nice day!");
    }

    private void printLibraryUsers() {
        LibraryUtilities.printSortedLibraryUsers(library, LibraryUtilities.sortUsersByLastThenFirstName);
    }

    private void borrowAPublicationToTheUser() {
        getRequestedUserAndPublication("Insert publication id to borrow it: ", "Insert user id: ");
        library.addAPublicationToUserBorrowedPublications(libraryUser, publication);
    }

    private void removeAPublicationFromUserBorrowedPublications() {
        getRequestedUserAndPublication("Insert publication id to reclaim it: ", "Insert user id: ");
        library.removeAPublicationFromUserBorrowedPublications(libraryUser, publication);
    }

    private void getRequestedUserAndPublication(String publicationMessage, String userMessage) {
        int publicationId = dataReader.readInputAndGetId(publicationMessage);
        int userId = dataReader.readInputAndGetId(userMessage);
        if (publicationId > library.getLibraryUsers().size() || userId > library.getPublications().size())
            throw new InputMismatchException("One of the ids is greater than the highest id number");
        libraryUser = library.getLibraryUsers().get(userId);
        publication = library.getPublications().get(publicationId);
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


    private enum Option {
        MAIN_MENU_EXIT(0, "Exit program"),
        MAIN_MENU_USERS(1, "Users menu"),
        MAIN_MENU_PUBLICATIONS(2, "Publications menu"),

        PRINT_LIBRARY_USERS(3, "Print library users"),
        BORROW_PUBLICATION_TO_USER(4, "Add book to user's book list"),
        CLAIM_PUBLICATION_FROM_USER(5, "Claim book from user"),
        REMOVE_USER(6, "Remove user"),
        ADD_USER(7, "Add user"),

        ADD_MAGAZINE(8, "Add magazine"),
        PRINT_MAGAZINES(9, "Print all magazines"),
        ADD_BOOK(10, "Add book"),
        PRINT_BOOKS(11, "Print all books");

        private int userInput;
        private String description;

        Option(int userInput, String description) {
            this.userInput = userInput;
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
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
