package com.example.demo.dto;

import com.example.demo.domain.ProductStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductUpdateDto {
    private UUID id;

    @NotBlank
    private String name;

    @Positive
    private int categoryId;

    @Positive
    private int supplierId;

    @Positive
    private BigDecimal price;

    private String description;

    private ProductStatus status;
}
