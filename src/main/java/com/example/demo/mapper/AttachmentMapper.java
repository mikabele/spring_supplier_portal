package com.example.demo.mapper;

import com.example.demo.domain.AttachmentDomain;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.AttachmentReadDto;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    imports = {UUID.class})
public interface AttachmentMapper {
  AttachmentReadDto toDto(AttachmentDomain domain);

  @Mappings({
    @Mapping(
        target = "product.id",
        expression = "java(UUID.fromString(attachmentCreateDto.getProductId()))")
  })
  AttachmentDomain fromDto(AttachmentCreateDto attachmentCreateDto);
}
