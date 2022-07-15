package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.exception.BadRequestException;
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
    public UUID addProduct(@RequestBody ProductCreateDto productCreateDto) throws Exception {
        var res = productService.addProduct(productCreateDto) ;
        if (res == null)
            throw new BadRequestException();
        return res;
    }

    @PutMapping
    public ProductReadDto updateProduct(@RequestBody ProductUpdateDto productUpdateDto) throws Exception {
        var res =  productService.updateProduct(productUpdateDto);
        if (res == null)
            throw new BadRequestException();
        return res;
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
    public UUID attachToProduct(@RequestBody AttachmentCreateDto attachmentCreateDto) throws Exception {
        var res = productService.attach(attachmentCreateDto);
        if (res == null)
            throw new BadRequestException();
        return res;
    }

    @DeleteMapping("/attachment/{id}")
    public Integer removeAttachment(@PathVariable UUID id) {
        return productService.removeAttachment(id);
    }
}
