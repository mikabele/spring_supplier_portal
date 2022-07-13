package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.AttachmentMapper;
import com.example.demo.mapper.CriteriaMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//TODO add throwing exceptions instead of optional or find something like Either class
@Service
public class ProductService {

    private ProductMapper productMapper;
    private CriteriaMapper criteriaMapper;
    private AttachmentMapper attachmentMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    public List<ProductReadDto> viewProducts() {
        var products = productRepository.findAll();
        return StreamSupport.stream(products.spliterator(), false).map(product -> productMapper.toDto(product)).collect(Collectors.toList());
    }

    public Optional<UUID> addProduct(ProductCreateDto productCreateDto) {
        var productDomain = productMapper.fromCreateDto(productCreateDto);
        var supplierDomain = supplierRepository.findById(productDomain.getSupplierId());
        return supplierDomain.flatMap(supplier -> {
            var categoryDomain = categoryRepository.findById(productDomain.getCategoryId());
            return categoryDomain.flatMap(category -> {
                var check = productRepository.findByNameAndSupplierId(productDomain.getName(), productDomain.getSupplierId());
                if (check.isEmpty()) {
                    return Optional.of(productRepository.save(productDomain).getId());
                } else {
                    return Optional.empty();
                }
            });
        });
    }

    public Optional<ProductReadDto> updateProduct(ProductUpdateDto productUpdateDto) {
        var productDomain = productMapper.fromUpdateDto(productUpdateDto);
        var supplierDomain = supplierRepository.findById(productDomain.getSupplierId());
        return supplierDomain.flatMap(supplier -> {
            var categoryDomain = categoryRepository.findById(productDomain.getCategoryId());
            return categoryDomain.flatMap(category -> {
                var check = productRepository.findByNameAndSupplierId(productDomain.getName(), productDomain.getSupplierId());
                if (check.isEmpty() && check.stream().allMatch(product -> product.getId() == productDomain.getId())) {
                    var res = productRepository.save(productDomain);
                    return Optional.of(productMapper.toDto(res));
                } else {
                    return Optional.empty();
                }
            });
        });
    }

    public Integer deleteProduct(UUID id) {
        var product = productRepository.findById(id);
        return product.map(p -> {
            productRepository.deleteById(id);
            return 1;
        }).orElse(0);
    }

    public List<ProductReadDto> searchByCriteria(CriteriaDto criteriaDto) {
        var criteriaDomain = criteriaMapper.fromDto(criteriaDto);
        return productRepository.findByOptionalName(criteriaDomain).stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
    }

    public Optional<UUID> attach(AttachmentCreateDto attachmentCreateDto) {
        var attachmentDomain = attachmentMapper.fromDto(attachmentCreateDto);
        var product = productRepository.findById(attachmentDomain.getProductId());
        return product.flatMap(p -> {
            if (!p.getAttachments().contains(attachmentDomain)) {
                return Optional.of(attachmentRepository.save(attachmentDomain).getId());
            } else
                return Optional.empty();
        });
    }

    public Integer removeAttachment(UUID id) {
        var att = attachmentRepository.findById(id);
        return att.map(a -> {
            attachmentRepository.deleteById(id);
            return 1;
        }).orElse(0);
    }
}
