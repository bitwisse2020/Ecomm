package com.example.common.Models;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum UserRole {
    // Power-of-2 values for bitmask operations
    GUEST(1),         // 0001
    CUSTOMER(2),      // 0010
    SELLER(4),        // 0100
    MODERATOR(8),     // 1000
    ADMIN(16),        // 0001 0000
    SUPER_ADMIN(32);  // 0010 0000

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    // Check if a role set contains this role
    public boolean isIn(int rolesBitmask) {
        return (rolesBitmask & this.value) == this.value;
    }

    // Combine roles into a bitmask
    public static int combine(UserRole... roles) {
        int combined = 0;
        for (UserRole role : roles) {
            combined |= role.value;
        }
        return combined;
    }

    // Get list of roles from bitmask
    public static Set<UserRole> decompose(int rolesBitmask) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.isIn(rolesBitmask))
                .collect(Collectors.toSet());
    }
}