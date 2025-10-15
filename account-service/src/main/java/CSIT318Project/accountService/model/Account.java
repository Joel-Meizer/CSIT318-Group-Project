package CSIT318Project.accountService.model;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    private Long id;
    private String username;
}
