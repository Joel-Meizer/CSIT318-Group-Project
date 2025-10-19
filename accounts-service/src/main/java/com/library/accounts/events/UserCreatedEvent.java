package com.library.accounts.events;

import java.io.Serializable;

public class UserCreatedEvent implements Serializable {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;

    public UserCreatedEvent() {}

    public UserCreatedEvent(Long userId, String email, String firstName, String lastName) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
