package com.example.demo.service;

import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductReadDto;
import com.example.demo.dto.ProductUpdateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    public List<ProductReadDto> viewProducts() {
        return List.of(new ProductReadDto(1));
    }

    public UUID addProduct(ProductCreateDto productCreateDto) {
        return UUID.randomUUID();
    }

    public ProductUpdateDto updateProduct(ProductUpdateDto productUpdateDto) {
        return null;
    }

    public Integer deleteProduct(UUID id) {
        return null;
    }
}
