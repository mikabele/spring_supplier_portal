package com.example.demo.controller;

import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductReadDto;
import com.example.demo.dto.ProductUpdateDto;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductReadDto> viewProducts() {
        return productService.viewProducts();
    }

    @PostMapping
    public UUID addProduct(@RequestBody ProductCreateDto productCreateDto) {
        return productService.addProduct(productCreateDto);
    }

    @PutMapping
    public ProductUpdateDto updateProduct(@RequestBody ProductUpdateDto productUpdateDto) {
        return productService.updateProduct(productUpdateDto);
    }

    @DeleteMapping("{id}")
    public Integer deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }
}
