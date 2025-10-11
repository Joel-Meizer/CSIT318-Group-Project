package CSIT318Project.resourceService.repository;

import CSIT318Project.resourceService.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
