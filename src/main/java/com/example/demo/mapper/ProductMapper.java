package com.example.demo.mapper;
import com.example.demo.domain.ProductDomain;
import com.example.demo.domain.ProductStatus;
import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductReadDto;
import com.example.demo.dto.ProductUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {}, imports = ProductStatus.class)

public interface ProductMapper {
//    @Mappings({
//            @Mapping(source = "name", target = "u.id"),
//            @Mapping(source = "eventId", target = "event.id"),
//            @Mapping(source = "orderStatus", target = "orderStatus", defaultExpression = "java(OrderStatus.CREATED)"),
//            @Mapping(target = "overallPrice", constant = "0.0")
//    })
    ProductDomain fromCreateDto(ProductCreateDto productCreateDto);

    ProductReadDto toDto(ProductDomain order);

    ProductDomain fromUpdateDto(ProductUpdateDto productUpdateDto);
}
