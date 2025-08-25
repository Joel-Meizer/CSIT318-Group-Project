package CSIT318Project.bookService.repository;

import CSIT318Project.bookService.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
