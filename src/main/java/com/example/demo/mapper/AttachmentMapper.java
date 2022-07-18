package com.example.demo.mapper;

import com.example.demo.domain.AttachmentDomain;
import com.example.demo.domain.CriteriaDomain;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.AttachmentReadDto;
import com.example.demo.dto.CriteriaDto;
import org.mapstruct.*;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttachmentMapper {
    AttachmentReadDto toDto (AttachmentDomain domain);

    @Mappings({
            @Mapping(source = "productId", target = "product.id")
    })
    AttachmentDomain fromDto(AttachmentCreateDto dto);
}
