package com.example.product_service.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Name Cannot be Null")
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("categoryID")
    @NotBlank(message = "Category Id Cannot be Null")
    private Long categoryID;
    @Positive
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("stock")
    @Min(0)
    private Integer stockQuantity;
    private String imageUrl;
}
