package com.company.data;

import java.time.LocalDate;

public class Book extends Publication {

    private enum BookCoverType {
        HARDCOVER, PAPERBACK;
    }

    private static final long serialVersionUID = -2415458008387491040L;

    private String author;
    private int pages;
    private String isbn;
    private BookCoverType bookCoverType;


    public Book(LocalDate dateOfPublication, String title, String publisher, String author, int pages, String isbn, String coverType) {
        super(dateOfPublication, title, publisher);
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
        this.bookCoverType = BookCoverType.valueOf(coverType.toUpperCase());
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookCoverType getBookCoverType() {
        return bookCoverType;
    }

    @Override
    public String toString() {
        return super.toString() + ' ' + author + ' ' + pages;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Book other = (Book) object;

        if (pages != other.pages) return false;
        if (author != null ? !author.equals(other.author) : other.author != null) return false;
        if (isbn != null ? !isbn.equals(other.isbn) : other.isbn != null) return false;
        return bookCoverType == other.bookCoverType;

    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = super.hashCode();
        result = prime * result + (author != null ? author.hashCode() : 0);
        result = prime * result + pages;
        result = prime * result + (isbn != null ? isbn.hashCode() : 0);
        result = prime * result + (bookCoverType != null ? bookCoverType.hashCode() : 0);
        return result;
    }
}


