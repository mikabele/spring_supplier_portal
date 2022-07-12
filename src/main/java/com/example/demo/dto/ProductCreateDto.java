package com.example.demo.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class ProductCreateDto {
    private String name;

    private int categoryId;

    private int supplierId;

    private float price;

    private Optional<String> description;
}
