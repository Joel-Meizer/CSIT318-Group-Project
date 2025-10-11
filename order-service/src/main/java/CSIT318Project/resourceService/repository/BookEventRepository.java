package CSIT318Project.resourceService.repository;

import CSIT318Project.resourceService.model.event.BookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookEventRepository extends JpaRepository<BookEvent, Long> {
}
