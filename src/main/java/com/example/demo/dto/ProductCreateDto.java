package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
public class ProductCreateDto {
    @NotBlank
    private String name;

    @Positive
    private int categoryId;

    @Positive
    private int supplierId;

    @Positive
    private BigDecimal price;

    private String description;
}
