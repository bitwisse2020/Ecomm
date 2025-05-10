package com.example.common.Models.service_user;

import com.example.common.Models.UserRole;

public interface UserPrinciple {
    Long getId();

    String getEmail();

    String getUsername();

    UserRole getRole(); // "ADMIN", "CUSTOMER", etc.

}
