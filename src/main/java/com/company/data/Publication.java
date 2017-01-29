package com.company.data;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;

public abstract class Publication implements Serializable {

    private static final long serialVersionUID = 1588310247784124327L;

    private static int nextPublicationId;

    private int publicationId;
    private LocalDate dateOfPublication;
    private String title;
    private String publisher;


    Publication(LocalDate dateOfPublication, String title, String publisher) {
        this.publicationId = ++nextPublicationId;
        this.dateOfPublication = dateOfPublication;
        this.title = title;
        this.publisher = publisher;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public LocalDate getDateOfPublication() {
        return dateOfPublication;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return dateOfPublication.getYear() + " " + title + ' ' + publisher;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Publication other = (Publication) object;

        if (dateOfPublication != null ? !dateOfPublication.equals(other.dateOfPublication) : other.dateOfPublication != null)
            return false;
        if (title != null ? !title.equals(other.title) : other.title != null) return false;
        return publisher != null ? publisher.equals(other.publisher) : other.publisher == null;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = dateOfPublication != null ? dateOfPublication.hashCode() : 0;
        result = prime * result + (title != null ? title.hashCode() : 0);
        result = prime * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
    }
}
