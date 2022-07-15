package com.example.demo.mapper;


import com.example.demo.domain.AttachmentDomain;
import com.example.demo.domain.CategoryDomain;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.AttachmentReadDto;
import com.example.demo.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CategoryMapper {
    CategoryDto toDto (CategoryDomain domain);

    CategoryDomain fromDto(CategoryDto dto);
}
