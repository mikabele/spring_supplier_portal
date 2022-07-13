package com.example.demo.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class ProductDomain {

    private UUID id;

    private String name;

    private int categoryId;

    private int supplierId;

    private float price;

    private Optional<String> description;

    private ProductStatus status;

    private LocalDateTime publicationDate;

    private List<AttachmentDomain> attachments;
}
