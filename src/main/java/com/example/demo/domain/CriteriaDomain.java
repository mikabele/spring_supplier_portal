package com.example.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

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
