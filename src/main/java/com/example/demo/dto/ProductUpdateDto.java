package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductUpdateDto {
    private String id;

    private String name;

    private int categoryId;

    private int supplierId;

    private float price;

    private String description;

    private ProductStatus status;
}
