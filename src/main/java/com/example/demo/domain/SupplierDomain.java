package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "supplier")
public class SupplierDomain {
    @Id
    private Integer id;
    private String name;
    private String address;
}
