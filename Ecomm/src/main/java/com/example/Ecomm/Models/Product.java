package com.example.Ecomm.Models;



import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "PRODUCT.PRODUCT")
@AllArgsConstructor(staticName = "builder")
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
//    @Column(columnDefinition = "UUID DEFAULT gen_random_uuid()", updatable = false)
//    private String product_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("category")
    private String category;
    @JsonProperty("price")
    private double price;


}
