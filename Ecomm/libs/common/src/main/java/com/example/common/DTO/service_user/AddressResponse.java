package com.example.common.DTO.service_user;

public record AddressResponse(
        String street,
        String city,
        String state,
        String postalCode,
        String country,
        boolean isDefault) {
}
