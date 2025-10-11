package com.library.accounts.dto;

import com.library.accounts.model.MembershipType;

public class CreateUserRequest {
    public String email;
    public String firstName;
    public String lastName;
    public MembershipType membershipType = MembershipType.STANDARD;
    public int membershipMonths = 12;
}

