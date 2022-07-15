package com.example.demo.domain;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Entity
@Table(name = "product")
@DynamicInsert
@DynamicUpdate
public class ProductDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;


    @OneToOne
    @JoinColumn(name = "category_id")
    private CategoryDomain category;

    @OneToOne
    @JoinColumn(name = "supplier_id")
    private SupplierDomain supplier;

    private float price;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private LocalDateTime publicationDate;

    @OneToMany(targetEntity = AttachmentDomain.class, mappedBy = "productId", fetch = FetchType.EAGER)
    private List<AttachmentDomain> attachments;
}
