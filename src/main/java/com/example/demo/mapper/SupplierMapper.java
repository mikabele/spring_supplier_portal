package com.example.demo.mapper;

import com.example.demo.domain.SupplierDomain;
import com.example.demo.dto.SupplierDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SupplierMapper {
  SupplierDto toDto(SupplierDomain domain);

  SupplierDomain fromDto(SupplierDto dto);
}
