package CSIT318Project.bookService.repository;

import CSIT318Project.bookService.model.event.BookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEventRepository extends JpaRepository<BookEvent, Long> {
}
