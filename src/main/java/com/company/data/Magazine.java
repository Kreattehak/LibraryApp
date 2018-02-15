package com.company.data;

import java.time.LocalDate;

public class Magazine extends Publication {

    private static final long serialVersionUID = 3874932116764309046L;

    private String category;

    public Magazine(LocalDate dateOfPublication, String title, String publisher, String category) {
        super(dateOfPublication, title, publisher);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return super.toString() + ' ' + category;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Magazine other = (Magazine) object;

        return category != null ? category.equals(other.category) : other.category == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
