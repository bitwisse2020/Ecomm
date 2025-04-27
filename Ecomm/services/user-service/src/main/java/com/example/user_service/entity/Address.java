package com.example.user_service.entity;

import com.example.common.Models.service_user.BaseAddress;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address implements BaseAddress {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private boolean isDefault;
}
