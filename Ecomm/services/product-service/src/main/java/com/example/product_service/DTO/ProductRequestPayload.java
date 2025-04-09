package com.example.product_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestPayload {
    @NotBlank(message = "Name Cannot be Null")
    @JsonProperty("name")
    private String name;
    @NotBlank(message = "Category Cannot be Null")
    @JsonProperty("category")
    private String category;
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "10000.00", message = "Price cannot exceed 10000.00")
    @JsonProperty("price")
    private double price;
}
