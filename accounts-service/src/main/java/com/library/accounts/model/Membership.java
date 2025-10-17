package com.library.accounts.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MembershipType type;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public Membership() {}

    public Membership(MembershipType type, LocalDate startDate, LocalDate endDate, boolean active) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
    }

    public Long getId() { return id; }
    public MembershipType getType() { return type; }
    public void setType(MembershipType type) { this.type = type; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

