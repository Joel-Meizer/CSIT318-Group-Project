package CSIT318Project.bookService.model;

import CSIT318Project.bookService.model.event.BookEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Book extends AbstractAggregateRoot<Book> {

    @Id
    private String isbn;

    @Column
    private String title;

    @Column
    @ElementCollection(fetch = FetchType.EAGER, targetClass=Long.class)
    private List<Long> availableLibraries = new ArrayList<>();

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getAvailableLibraries() {
        return availableLibraries;
    }

    public void addAvailableLibrary(Long libraryId) {
        this.availableLibraries.add(libraryId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", availableLibraries=" + availableLibraries.toString() +
                '}';
    }

    public boolean checkAvailability(long libraryId) {
        return this.availableLibraries.contains(Optional.of(libraryId));
    }

    public void borrowFrom(long libraryId) {
        if (this.checkAvailability(libraryId)) {
            this.availableLibraries.remove((int) libraryId);
            BookEvent event = new BookEvent();
            event.setEventName("borrow");
            event.setBookIsbn(this.getIsbn());
            event.setBookTitle(this.getTitle());
            event.setLibraryId(Long.valueOf(libraryId));
            /**
             * Method to register the event
             * @param event
             */
            registerEvent(event);
        }
    }

    public void returnTo(long libraryId) {
        this.availableLibraries.add(Long.valueOf(libraryId));
    }
}
