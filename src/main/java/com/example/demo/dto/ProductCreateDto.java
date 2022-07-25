package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductCreateDto {
  @NotBlank private String name;

  @NotNull @Positive private int categoryId;

  @NotNull @Positive private int supplierId;

  @NotNull @Positive private BigDecimal price;

  private String description;
}
