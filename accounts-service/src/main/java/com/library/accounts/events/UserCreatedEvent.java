package com.library.accounts.events;

import com.library.accounts.model.UserAccount;

public class UserCreatedEvent {
    private final Long userId;
    private final String email;
    private final String firstName;
    private final String lastName;

    public UserCreatedEvent(UserAccount user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}
