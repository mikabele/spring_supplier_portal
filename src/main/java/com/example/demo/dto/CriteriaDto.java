package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriteriaDto {
  private String name;
  private String categoryName;
  private String description;
  private String supplierName;
  private Float minPrice;
  private Float maxPrice;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
