package com.example.demo.mapper;

import com.example.demo.domain.CriteriaDomain;
import com.example.demo.domain.ProductStatus;
import com.example.demo.dto.CriteriaDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CriteriaMapper {

    CriteriaDto toDto (CriteriaDomain domain);

    CriteriaDomain fromDto(CriteriaDto dto);
}
