package CSIT318Project.accountService.repository;

import CSIT318Project.accountService.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
