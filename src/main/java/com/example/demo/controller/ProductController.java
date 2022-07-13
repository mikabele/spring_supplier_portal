package com.example.demo.controller;

import com.example.demo.dto.*;
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
        return productService.addProduct(productCreateDto).orElseThrow();
    }

    @PutMapping
    public ProductReadDto updateProduct(@RequestBody ProductUpdateDto productUpdateDto) {
        return productService.updateProduct(productUpdateDto).orElseThrow();
    }

    @DeleteMapping("{id}")
    public Integer deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<ProductReadDto> search(@RequestBody CriteriaDto criteriaDto) {
        return productService.searchByCriteria(criteriaDto);
    }

    @PostMapping("/attachment")
    public UUID attachToProduct(@RequestBody AttachmentCreateDto attachmentCreateDto) {
        return productService.attach(attachmentCreateDto).orElseThrow();
    }

    @DeleteMapping("/attachment/{id}")
    public Integer removeAttachment(@RequestParam UUID id) {
        return productService.removeAttachment(id);
    }
}
