package com.example.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
public class CriteriaDomain {
    private Optional<String> name = Optional.empty();
    private Optional<String> categoryName = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<String> supplierName = Optional.empty();
    private Optional<Float> minPrice = Optional.empty();
    private Optional<Float> maxPrice = Optional.empty();
    private Optional<LocalDateTime> startDate = Optional.empty();
    private Optional<LocalDateTime> endDate = Optional.empty();

}
