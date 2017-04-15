package com.company.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LibraryUser implements Serializable {

    private static final long serialVersionUID = 8752396576502328076L;

    private static int nextUserId;

    private final int userId;
    private String firstName;
    private String lastName;
    private final String personalIdentityNumber;
    private List<Publication> borrowedPublications;


    public LibraryUser(String firstName, String lastName, String personalIdentityNumber) {
        this.userId = ++nextUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalIdentityNumber = personalIdentityNumber;
        this.borrowedPublications = new ArrayList<>(4);
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalIdentityNumber() {
        return personalIdentityNumber;
    }

    public List<Publication> getBorrowedPublications() {
        return borrowedPublications;
    }

    @Override
    public String toString() {
        return userId + ". " + firstName + ' ' + lastName + ", amount of borrowed books: " + borrowedPublications.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        LibraryUser other = (LibraryUser) object;

        if (firstName != null ? !firstName.equals(other.firstName) : other.firstName != null) return false;
        if (lastName != null ? !lastName.equals(other.lastName) : other.lastName != null) return false;
        if (personalIdentityNumber != null ? !personalIdentityNumber.equals(other.personalIdentityNumber)
                : other.personalIdentityNumber != null) {
            return false;
        }
        return borrowedPublications != null ? borrowedPublications.equals(other.borrowedPublications) :
                other.borrowedPublications == null;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = firstName != null ? firstName.hashCode() : 0;
        result = prime * result + (lastName != null ? lastName.hashCode() : 0);
        result = prime * result + (personalIdentityNumber != null ? personalIdentityNumber.hashCode() : 0);
        result = prime * result + (borrowedPublications != null ? borrowedPublications.hashCode() : 0);
        return result;
    }
}
