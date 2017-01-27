package com.company.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LibraryUser implements Serializable {

    private static final long serialVersionUID = 7802848302154147728L;

    private static int nextUserId;

    private final int userId;
    private String firstName;
    private String lastName;
    private final String personalIdentityNumber;
    private List<Publication> borrowedPublications;
    private List<Publication> userRentHistory;

    public LibraryUser(String firstName, String lastName, String personalIdentityNumber) {
        this.userId = ++nextUserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalIdentityNumber = personalIdentityNumber;
        this.borrowedPublications = new ArrayList<>(4);
        this.userRentHistory = new LinkedList<>();
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

    public List<Publication> getUserRentHistory() {
        return userRentHistory;
    }

    @Override
    public String toString() {
        return userId + ". " +  firstName + ' ' + lastName + ", amount of borrowed books: " + borrowedPublications.size();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        LibraryUser other = (LibraryUser) object;

//        if(userId != other.userId) return false;
        if (firstName != null ? !firstName.equals(other.firstName) : other.firstName != null) return false;
        if (lastName != null ? !lastName.equals(other.lastName) : other.lastName != null) return false;
        if (personalIdentityNumber != null ? !personalIdentityNumber.equals(other.personalIdentityNumber)
                : other.personalIdentityNumber != null) {
            return false;
        }
        if (borrowedPublications != null ? !borrowedPublications.equals(other.borrowedPublications) :
                other.borrowedPublications != null) {
            return false;
        }
        return userRentHistory != null ? userRentHistory.equals(other.userRentHistory) :
                other.userRentHistory == null;

    }

    @Override
    public int hashCode() {
        final int prime = 17;
//        int result = userId;
        int result = firstName != null ? firstName.hashCode() : 0;
        result = prime  * result + (lastName != null ? lastName.hashCode() : 0);
        result = prime  * result + (personalIdentityNumber != null ? personalIdentityNumber.hashCode() : 0);
        result = prime  * result + (borrowedPublications != null ? borrowedPublications.hashCode() : 0);
        result = prime  * result + (userRentHistory != null ? userRentHistory.hashCode() : 0);
        return result;
    }
}
