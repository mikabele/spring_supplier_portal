package com.example.demo.service;

import com.example.demo.domain.AttachmentDomain;
import com.example.demo.domain.ProductDomain;
import com.example.demo.dto.*;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.MappingException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.AttachmentMapper;
import com.example.demo.mapper.CriteriaMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//TODO add throwing exceptions instead of optional or find something like Either class
@Service
public class ProductService {

	private final ProductMapper productMapper;
	private final CriteriaMapper criteriaMapper;
	private final AttachmentMapper attachmentMapper;

	private final SupplierRepository supplierRepository;

	private final CategoryRepository categoryRepository;

	private final ProductRepository productRepository;

	private final AttachmentRepository attachmentRepository;

	public ProductService(ProductMapper productMapper, CriteriaMapper criteriaMapper, AttachmentMapper attachmentMapper, SupplierRepository supplierRepository, CategoryRepository categoryRepository, ProductRepository productRepository, AttachmentRepository attachmentRepository) {
		this.productMapper = productMapper;
		this.criteriaMapper = criteriaMapper;
		this.attachmentMapper = attachmentMapper;
		this.supplierRepository = supplierRepository;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.attachmentRepository = attachmentRepository;
	}

	public List<ProductReadDto> viewProducts() {
		var products = productRepository.findAll();
		return StreamSupport.stream(products.spliterator(), false).map(productMapper::toDto).collect(Collectors.toList());
	}


	private void checkProductUniqueness(ProductDomain productDomain) {
		var supplierDomain = supplierRepository.findById(productDomain.getSupplier().getId());
		if (supplierDomain.isEmpty()) {
			throw new NotFoundException("Supplier with id " + productDomain.getSupplier().getId() + " not found.");
		}
		var categoryDomain = categoryRepository.findById(productDomain.getCategory().getId());
		if (categoryDomain.isEmpty()) {
			throw new NotFoundException("Category with id " + productDomain.getCategory().getId() + " not found.");
		}
	}

	public ProductReadDto addProduct(ProductCreateDto productCreateDto) {
		var productDomain = productMapper.fromCreateDto(productCreateDto);
		if (productDomain == null){
			throw new MappingException("Product mapping error");
		}
		checkProductUniqueness(productDomain);
		var check = productRepository.findByNameAndSupplierId(productDomain.getName(), productDomain.getSupplier().getId());
		if (check.isPresent()) {
			throw new AlreadyExistsException("Product with name " + productDomain.getName() + " and supplier id " + productDomain.getSupplier().getId() + " already exists");
		}
		return productMapper.toDto(productRepository.save(productDomain));

	}

	public ProductReadDto updateProduct(ProductUpdateDto productUpdateDto) {
		var productDomain = productMapper.fromUpdateDto(productUpdateDto);
		if (productDomain == null){
			throw new MappingException("Product mapping error");
		}
		checkProductUniqueness(productDomain);
		var existingProduct = productRepository.findById(productDomain.getId());
		if (existingProduct.isEmpty()) {
			throw  new NotFoundException("Product with id " + productDomain.getId() + " not found");
		}
		var check = productRepository.findByNameAndSupplierId(productDomain.getName(), productDomain.getSupplier().getId());
		if (check.isPresent() && check.get().getId() != existingProduct.get().getId()) {
			throw new AlreadyExistsException("Product with name " + productDomain.getName() + " and supplier id " + productDomain.getSupplier().getId() + " already exists");
		}
		var res = productRepository.save(productDomain);
		return productMapper.toDto(res);
	}

	public void deleteProduct(UUID id) {
		var product = productRepository.findById(id);
		if (product.isEmpty()) {
			throw new NotFoundException("Product with id " + id + " not found");
		}
		productRepository.deleteById(id);
	}

	public List<ProductReadDto> searchByCriteria(CriteriaDto criteriaDto) {
		var criteriaDomain = criteriaMapper.fromDto(criteriaDto);
		if (criteriaDomain == null){
			throw new MappingException("Criteria mapping error");
		}
		var res = productRepository.searchByCriteria(criteriaDomain.getName(), criteriaDomain.getCategoryName(),
				criteriaDomain.getDescription(), criteriaDomain.getSupplierName(), criteriaDomain.getMinPrice(),
				criteriaDomain.getMaxPrice(), criteriaDomain.getStartDate(), criteriaDomain.getEndDate());
		return res.stream().map(productMapper::toDto).collect(Collectors.toList());
	}

	public AttachmentReadDto attach(AttachmentCreateDto attachmentCreateDto) {
		var attachmentDomain = attachmentMapper.fromDto(attachmentCreateDto);
		if (attachmentDomain == null){
			throw new MappingException("Attachment mapping error");
		}
		var product = productRepository.findById(attachmentDomain.getProduct().getId());
		if (product.isEmpty()) {
			throw new NotFoundException("Product with id " + attachmentDomain.getProduct().getId() + " not found");
		}
		var attachments = product.get().getAttachments().stream().map(AttachmentDomain::getAttachment).toList();
		if (attachments.contains(attachmentDomain.getAttachment())) {
			throw new AlreadyExistsException("Attachment with url " + attachmentDomain.getAttachment() + " and product id " + attachmentDomain.getProduct().getId() + " already exists");
		}
		return attachmentMapper.toDto(attachmentRepository.save(attachmentDomain));
	}

	public void removeAttachment(UUID id) {
		var att = attachmentRepository.findById(id);
		if (att.isEmpty()) {
			throw new NotFoundException("Attachement with id " + id + " not found");
		}
		attachmentRepository.deleteById(id);
	}
}
