package com.example.demo.mapper;

import com.example.demo.domain.ProductDomain;
import com.example.demo.domain.ProductStatus;
import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductReadDto;
import com.example.demo.dto.ProductUpdateDto;
import com.example.demo.exception.MappingException;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    uses = {AttachmentMapper.class, CategoryMapper.class, SupplierMapper.class},
    imports = {ProductStatus.class, LocalDateTime.class, BigDecimal.class},
    unexpectedValueMappingException = MappingException.class)
public interface ProductMapper {

  @Mappings({
    @Mapping(source = "categoryId", target = "category.id"),
    @Mapping(source = "supplierId", target = "supplier.id")
  })
  ProductDomain fromCreateDto(ProductCreateDto productCreateDto);

  ProductReadDto toDto(ProductDomain order);

  @Mappings({
    @Mapping(source = "categoryId", target = "category.id"),
    @Mapping(source = "supplierId", target = "supplier.id")
  })
  ProductDomain fromUpdateDto(ProductUpdateDto productUpdateDto);
}
