package com.example.demo.util;

import com.example.demo.domain.ProductStatus;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;

@Component
public class ProductStatusEnumConverter implements AttributeConverter<ProductStatus, String> {

  @Override
  public String convertToDatabaseColumn(ProductStatus productStatus) {
    return productStatus.toString().toLowerCase();
  }

  @Override
  public ProductStatus convertToEntityAttribute(String s) {
    return ProductStatus.valueOf(s.toUpperCase());
  }
}
