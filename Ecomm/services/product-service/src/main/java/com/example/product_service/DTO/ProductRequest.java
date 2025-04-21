package com.example.product_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 200, message = "Product name cannot exceed 200 characters")
    private String name;

    @Size(max = 10000, message = "Description cannot exceed 10000 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    @Digits(integer = 8, fraction = 2, message = "Price format invalid (e.g., 12345678.90)")
    private BigDecimal price;


    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stock;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    // Consider adding @URL validation if format needs to be strictly enforced
    private String imageUrl;
}
