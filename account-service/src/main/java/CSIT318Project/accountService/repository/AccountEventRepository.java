package CSIT318Project.accountService.repository;

import CSIT318Project.accountService.model.event.BookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountEventRepository extends JpaRepository<BookEvent, Long> {
}
