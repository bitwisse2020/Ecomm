package com.example.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRegistrationRequest(@NotBlank @Email String email, @Size(min = 8, max = 100) String password,
                                      @NotBlank String firstName, @NotBlank String lastName) {
}
