package com.example.demo.dto;

import com.example.demo.domain.ProductStatus;
import com.example.demo.validation.UuidValidation;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductUpdateDto {
  @UuidValidation @NotNull private String id;

  @NotBlank private String name;

  @Positive private int categoryId;

  @Positive private int supplierId;

  @Positive private BigDecimal price;

  private String description;

  private ProductStatus status;
}
