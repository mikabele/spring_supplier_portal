package com.example.demo.dto;

import com.example.demo.domain.ProductStatus;
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
