package com.library.accounts.events;

public class MembershipCancelledEvent {
    private final Long userId;

    public MembershipCancelledEvent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() { return userId; }
}
