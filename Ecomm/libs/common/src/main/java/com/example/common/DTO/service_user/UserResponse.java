package com.example.common.DTO.service_user;

import com.example.common.Models.UserRole;

import java.util.List;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String username,
        UserRole role,
        boolean isActive,
        List<AddressResponse> addresses) {
}
