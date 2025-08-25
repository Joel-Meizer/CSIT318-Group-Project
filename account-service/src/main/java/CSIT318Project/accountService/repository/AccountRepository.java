package CSIT318Project.accountService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Book, String> {
}
