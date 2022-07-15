package com.example.demo.dto;

import com.example.demo.domain.ProductStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductUpdateDto {
    private UUID id;

    private String name;

    private int categoryId;

    private int supplierId;

    private float price;

    private String description;

    private ProductStatus status;
}
