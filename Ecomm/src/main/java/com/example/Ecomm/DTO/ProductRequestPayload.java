package com.example.Ecomm.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestPayload {
    @JsonProperty("name")
    private String name;
    @JsonProperty("category")
    private String category;
    @JsonProperty("price")
    private double price;
}
