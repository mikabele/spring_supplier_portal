package com.example.demo.mapper;

import com.example.demo.domain.AttachmentDomain;
import com.example.demo.domain.CriteriaDomain;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.AttachmentReadDto;
import com.example.demo.dto.CriteriaDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AttachmentMapper {
    AttachmentReadDto toDto (AttachmentDomain domain);

    AttachmentDomain fromDto(AttachmentCreateDto dto);
}
