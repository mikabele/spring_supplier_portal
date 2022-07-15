package com.example.demo.service;

import com.example.demo.domain.AttachmentDomain;
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

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CriteriaMapper criteriaMapper;
    @Autowired
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

    public UUID addProduct(ProductCreateDto productCreateDto) {
        var productDomain = productMapper.fromCreateDto(productCreateDto);
        var supplierDomain = supplierRepository.findById(productDomain.getSupplier().getId());
        return supplierDomain.flatMap(supplier -> {
            var categoryDomain = categoryRepository.findById(productDomain.getCategory().getId());
            return categoryDomain.map(category -> {
                var check = productRepository.findByNameAndSupplierId(productDomain.getName(), productDomain.getSupplier().getId());
                if (check.isEmpty()) {
                    return productRepository.save(productDomain).getId();
                } else {
                    return null;
                }
            });
        }).orElse(null);
    }

    public ProductReadDto updateProduct(ProductUpdateDto productUpdateDto) {
        var productDomain = productMapper.fromUpdateDto(productUpdateDto);
        var supplierDomain = supplierRepository.findById(productDomain.getSupplier().getId());
        return supplierDomain.flatMap(supplier -> {
            var categoryDomain = categoryRepository.findById(productDomain.getCategory().getId());
            return categoryDomain.map(category -> {
                var check = productRepository.findByNameAndSupplierId(productDomain.getName(), productDomain.getSupplier().getId());
                if (check.isEmpty() && check.stream().allMatch(product -> product.getId() == productDomain.getId())) {
                    var res = productRepository.save(productDomain);
                    return productMapper.toDto(res);
                } else {
                    return null;
                }
            });
        }).orElse(null);
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
        return productRepository
                .searchByCriteria(criteriaDomain.getName(), criteriaDomain.getCategoryName(), criteriaDomain.getDescription(), criteriaDomain.getSupplierName(), criteriaDomain.getMinPrice(), criteriaDomain.getMaxPrice(), criteriaDomain.getStartDate(), criteriaDomain.getEndDate()).stream().map(product -> productMapper.toDto(product)).collect(Collectors.toList());
    }

    public UUID attach(AttachmentCreateDto attachmentCreateDto) {
        var attachmentDomain = attachmentMapper.fromDto(attachmentCreateDto);
        var product = productRepository.findById(attachmentDomain.getProductId());
        return product.map(p -> {
            if (!p.getAttachments().stream().map(AttachmentDomain::getAttachment).toList().contains(attachmentDomain.getAttachment())) {
                return attachmentRepository.save(attachmentDomain).getId();
            } else
                return null;
        }).orElse(null);
    }

    public Integer removeAttachment(UUID id) {
        var att = attachmentRepository.findById(id);
        return att.map(a -> {
            attachmentRepository.deleteById(id);
            return 1;
        }).orElse(0);
    }
}
