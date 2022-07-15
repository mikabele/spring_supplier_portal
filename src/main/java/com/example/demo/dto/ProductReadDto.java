package com.example.demo.dto;

import com.example.demo.domain.ProductStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ProductReadDto {

    private UUID id;
    private String name;
    private CategoryDto category;
    private SupplierDto supplier;
    private float price;
    private String description;
    private ProductStatus status;
    private LocalDateTime publicationDate;
    private List<AttachmentReadDto> attachments;
}
