package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<ProductReadDto> viewProducts() {
		return productService.viewProducts();
	}

	@PostMapping
	public ProductReadDto addProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
		var res = productService.addProduct(productCreateDto);
		return res;
	}

	@PutMapping
	public ProductReadDto updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
		var res = productService.updateProduct(productUpdateDto);
		return res;
	}

	@DeleteMapping("{id}")
	public void deleteProduct(@PathVariable UUID id) {
		productService.deleteProduct(id);
	}

	@GetMapping("/search")
	public List<ProductReadDto> search(@RequestBody CriteriaDto criteriaDto) {
		return productService.searchByCriteria(criteriaDto);
	}

	@PostMapping("/attachment")
	public AttachmentReadDto attachToProduct(@Valid @RequestBody AttachmentCreateDto attachmentCreateDto) {
		var res = productService.attach(attachmentCreateDto);
		return res;
	}

	@DeleteMapping("/attachment/{id}")
	public void removeAttachment(@PathVariable UUID id) {
		productService.removeAttachment(id);
	}
}
