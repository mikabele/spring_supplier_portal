package com.example.demo.mapper;

import com.example.demo.domain.AttachmentDomain;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.AttachmentReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttachmentMapper {
  AttachmentReadDto toDto(AttachmentDomain domain);

  @Mappings({@Mapping(source = "productId", target = "product.id")})
  AttachmentDomain fromDto(AttachmentCreateDto dto);
}
