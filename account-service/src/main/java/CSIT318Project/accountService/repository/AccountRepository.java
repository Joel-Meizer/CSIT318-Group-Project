package CSIT318Project.accountService.repository;

import CSIT318Project.bookService.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Book, String> {
}
