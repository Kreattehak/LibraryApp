package com.company.util;

import com.company.data.Book;
import com.company.data.LibraryUser;
import com.company.data.Magazine;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataReader {

    private Scanner sc;

    public DataReader() {
        sc = new Scanner(System.in);
    }

    public int getInput() {
        int number = 0;
        try {
            number = sc.nextInt();
        } catch (NumberFormatException e) {
            throw e;
        } finally {
            sc.nextLine();
        }

        return number;
    }

    public Book readInputAndCreateBook() {
        System.out.println("Year of publication: ");
        String input = sc.nextLine().trim().concat("/01/01");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(input, formatter);
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Publisher: ");
        String publisher = sc.nextLine();
        System.out.println("Author: ");
        String author = sc.nextLine();
        System.out.println("Number of pages: ");
        int pages = Integer.parseInt(sc.nextLine());
        System.out.println("ISBN: ");
        String isbn = sc.nextLine().trim().replaceAll("-", "");
        if (date.getYear() < 2007 && isbn.length() != 10 || date.getYear() >= 2007 && isbn.length() != 13) {
            throw new InputMismatchException("ISBN is not valid. Books released till 31 December 2006 have ten digits," +
                    "books release after have thirteen digits.");
        }
        System.out.println("Cover type [Hardcover/Paperback]: ");
        String coverType = sc.nextLine().toUpperCase();
        if (!coverType.equals("HARDCOVER") && !coverType.equals("PAPERBACK"))
            throw new InputMismatchException("Cover type is not valid.");

        Book book = new Book(date, title, publisher, author, pages, isbn, coverType);
        System.out.println("Book " + title + " created by " + author + " published by " + publisher
                + " which id is: " + book.getPublicationId() + " was created.");

        return book;
    }

    public Magazine readInputAndCreateMagazine() {
        System.out.println("Date of publication [YYYY/MM/DD]: ");
        String input = sc.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(input, formatter);
        System.out.println("Title: ");
        String title = sc.nextLine();
        System.out.println("Publisher: ");
        String publisher = sc.nextLine();
        System.out.println("Magazine category: ");
        String category = sc.nextLine();

        Magazine magazine = new Magazine(date, title, publisher, category);
        System.out.println("Magazine " + title + " published by " + publisher + " which id is: "
                + magazine.getPublicationId() + " was created.");

        return magazine;
    }

    public LibraryUser readInputAndCreateLibraryUser() {
        System.out.println("User first name: ");
        String firstName = sc.nextLine();
        System.out.println("User last name: ");
        String lastName = sc.nextLine();
        System.out.println("User personal identity number: ");
        String personIdentityNumber = sc.nextLine();
        if (personIdentityNumber.length() != 11)
            throw new InputMismatchException("Personal identity number is not valid.");

        LibraryUser libraryUser = new LibraryUser(firstName, lastName, personIdentityNumber);
        System.out.println("User " + firstName + ' ' + lastName + " which id is: "
                + libraryUser.getUserId() + " was created.");

        return libraryUser;
    }

    public int readInputAndGetId(String message) {
        System.out.println(message);
        int id = Integer.parseInt(sc.nextLine());
        if (id < 1)
            throw new InputMismatchException("The lowest id is 1.");

        return id;
    }

    public void closeScanner() {
        sc.close();
    }

}