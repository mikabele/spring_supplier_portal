package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "attachment")
public class AttachmentDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String attachment;

    @ManyToOne(targetEntity = ProductDomain.class)
    @JoinColumn(name = "product_id",nullable = false)
    private ProductDomain product;
}
