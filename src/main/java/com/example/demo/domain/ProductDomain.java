package com.example.demo.domain;

import com.example.demo.util.ProductStatusEnumConverter;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

  @Column(length = 10, precision = 2)
  private BigDecimal price;

  private String description;

  @Convert(converter = ProductStatusEnumConverter.class)
  private ProductStatus status;

  private LocalDateTime publicationDate;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @ToString.Exclude
  private List<AttachmentDomain> attachments;
}
