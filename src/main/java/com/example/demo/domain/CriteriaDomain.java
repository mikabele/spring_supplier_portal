package com.example.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CriteriaDomain {
  private String name;
  private String categoryName;
  private String description;
  private String supplierName;
  private Float minPrice;
  private Float maxPrice;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
}
