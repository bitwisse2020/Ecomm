package com.example.product_service.DTO;



import jakarta.validation.constraints.*;
        import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO for updating an existing product.
 * All the Fields are optional but there are some constraints that has to be followed.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {

    @Size(max = 200, message = "Product name cannot exceed 200 characters")
    private String name;

    @Size(max = 10000, message = "Description cannot exceed 10000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be positive")
    @Digits(integer = 8, fraction = 2, message = "Price format invalid (e.g., 12345678.90)")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    private Long categoryId;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    // Consider adding @URL validation if format needs to be strictly enforced
    private String imageUrl;
}
