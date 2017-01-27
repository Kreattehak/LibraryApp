package com.company.util;

import com.company.data.Library;
import com.company.data.LibraryUser;
import com.company.data.Publication;

import java.util.Comparator;

public class LibraryUtilities {

    public static final Comparator<LibraryUser> sortUsersByLastThenFirstName =
            Comparator.comparing(LibraryUser::getLastName).thenComparing(LibraryUser::getFirstName);
    public static final Comparator<LibraryUser> sortUsersByAmountOfBorrowedPublications =
            Comparator.comparing((libraryUser -> libraryUser.getBorrowedPublications().size()), Comparator.reverseOrder());
    public static final Comparator<LibraryUser> sortUsersById =
            Comparator.comparing(LibraryUser::getUserId);

    public static void printSortedLibraryUsers(Library library, Comparator<LibraryUser> comparator) {
        library.getLibraryUsers().values().stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public static void printConcretePublicationsSortedByYears(Library library, Class<? extends Publication> clazz) {
        library.getPublications().values().stream()
                .filter(clazz::isInstance)
                .sorted(Comparator.nullsLast(Comparator.comparing(Publication::getDateOfPublication)))
                .forEach(System.out::println);
    }

    public static long getAmountOfConcretePublications(Library library, Class<? extends Publication> clazz) {
        return library.getPublications().values().stream().filter(clazz::isInstance).count();
    }

}
