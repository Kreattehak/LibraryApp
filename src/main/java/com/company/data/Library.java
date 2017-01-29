package com.company.data;

import com.company.util.LibraryUtilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements Serializable {

    private static final long serialVersionUID = -403054986771393677L;

    private Map<Integer, Publication> integerToPublicationMap;
    private Map<Integer, LibraryUser> integerToLibraryUserMap;

    public Library() {
        this.integerToPublicationMap = new HashMap<>();
        this.integerToLibraryUserMap = new HashMap<>();
    }

    public Map<Integer, Publication> getPublications() {
        return integerToPublicationMap;
    }

    public Map<Integer, LibraryUser> getLibraryUsers() {
        return integerToLibraryUserMap;
    }

    public void addPublication(Publication publication) {
        integerToPublicationMap.putIfAbsent(publication.getPublicationId(), publication);
    }

    public Publication removePublication(Publication publication) {
        return integerToPublicationMap.computeIfPresent(publication.getPublicationId(),
                (key, map) -> integerToPublicationMap.remove(key));
    }

    public void addUser(LibraryUser libraryUser) {
        integerToLibraryUserMap.putIfAbsent(libraryUser.getUserId(), libraryUser);
    }

    public void removeUser(LibraryUser libraryUser) {
        integerToLibraryUserMap.computeIfPresent(libraryUser.getUserId(),
                (key, map) -> integerToLibraryUserMap.remove(key));
    }

    public void addAPublicationToUserBorrowedPublications(LibraryUser libraryUser, Publication publication) {
        List<Publication> borrowedPublications = libraryUser.getBorrowedPublications();
        if (borrowedPublications.size() > 4)
            throw new IndexOutOfBoundsException("User already have borrow 4 publications, limit reached.");
        libraryUser.getBorrowedPublications().add(publication);
    }

    public void removeAPublicationFromUserBorrowedPublications(LibraryUser libraryUser, Publication publication) {
        libraryUser.getBorrowedPublications().remove(publication);
    }

    @Override
    public String toString() {
        return "Library has " + integerToPublicationMap.size() + " publications, "
                + LibraryUtilities.getAmountOfConcretePublications(this, Book.class)
                + " books and " + LibraryUtilities.getAmountOfConcretePublications(this, Magazine.class) + " magazines\n" +
                "Library has " + integerToLibraryUserMap.size() + " users";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Library other = (Library) object;

        if (integerToPublicationMap != null ? !integerToPublicationMap.equals(other.integerToPublicationMap)
                : other.integerToPublicationMap != null) {
            return false;
        }
        return integerToLibraryUserMap != null ? integerToLibraryUserMap.equals(other.integerToLibraryUserMap)
                : other.integerToLibraryUserMap == null;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = integerToPublicationMap != null ? integerToPublicationMap.hashCode() : 0;
        result = prime * result + (integerToLibraryUserMap != null ? integerToLibraryUserMap.hashCode() : 0);
        return result;
    }
}
