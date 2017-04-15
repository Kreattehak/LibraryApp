package com.company.util;

import com.company.data.Library;
import com.company.data.LibraryUser;
import com.company.data.Publication;

import java.util.Comparator;
import java.util.Map;

public class LibraryUtilities {

    public static final Comparator<LibraryUser> sortUsersByLastThenFirstName =
            Comparator.comparing(LibraryUser::getLastName).thenComparing(LibraryUser::getFirstName);
    public static final Comparator<LibraryUser> sortUsersByAmountOfBorrowedPublications =
            Comparator.comparing((libraryUser -> libraryUser.getBorrowedPublications().size()), Comparator.reverseOrder());
    public static final Comparator<LibraryUser> sortUsersById =
            Comparator.comparing(LibraryUser::getUserId);

    private LibraryUtilities() {
        //Non-instantiable utility class
    }

    public static void printSortedLibraryUsers(Library library, Comparator<LibraryUser> comparator) {
        Map<Integer, LibraryUser> users = library.getLibraryUsers();
        if (users.size() == 0) {
            System.out.println("User list is empty!");
        }
        users.values().stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    public static void printConcretePublicationsSortedByYears(Library library, Class<? extends Publication> clazz) {
        Map<Integer, Publication> publications = library.getPublications();
        if (publications.size() == 0) {
            System.out.println("Publication list is empty!");
        }
        publications.values().stream()
                .filter(clazz::isInstance)
                .sorted(Comparator.nullsLast(Comparator.comparing(Publication::getDateOfPublication)))
                .forEach(System.out::println);
    }

    public static long getAmountOfConcretePublications(Library library, Class<? extends Publication> clazz) {
        return library.getPublications().values().stream().filter(clazz::isInstance).count();
    }

}
