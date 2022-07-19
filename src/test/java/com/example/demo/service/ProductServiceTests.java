package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductUpdateDto;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.AttachmentMapper;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.CriteriaMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceTests {


	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private AttachmentRepository attachmentRepository;

	@MockBean
	private SupplierRepository supplierRepository;

	@MockBean
	private CategoryRepository categoryRepository;
	private ProductService productService;

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private CriteriaMapper criteriaMapper;
	@Autowired
	private AttachmentMapper attachmentMapper;


	@BeforeEach
	void initUseCase() {
		productService = new ProductService(productMapper, criteriaMapper, attachmentMapper, supplierRepository,
				categoryRepository, productRepository, attachmentRepository);
	}

	ProductDomain generateExpectedProduct() {
		var product = new ProductDomain();
		product.setName("test");
		product.setId(UUID.randomUUID());
		var supplier = new SupplierDomain();
		supplier.setId(1);
		var category = new CategoryDomain();
		category.setId(1);
		product.setSupplier(supplier);
		product.setCategory(category);
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
		when(supplierRepository.findById(1)).thenReturn(Optional.of(supplier));
		when(productRepository.save(any(ProductDomain.class))).thenReturn(product);
		when(productRepository.findByNameAndSupplierId(eq("test"), eq(1))).thenReturn(Optional.empty());
		when(productRepository.findByNameAndSupplierId(not(eq("test")), any(Integer.class))).thenReturn(Optional.of(new ProductDomain()));
		return product;
	}

	// Add product scenario tests
	@Test
	public void addProductSuccessfulTest() {

		var product = generateExpectedProduct();

		ProductCreateDto productCreateDto = new ProductCreateDto("test", 1, 1, BigDecimal.ONE, null);
		assertThat(productService.addProduct(productCreateDto), Matchers.equalTo(productMapper.toDto(product)));
	}

	@Test
	public void addProductWrongCategoryTest() {
		var product = generateExpectedProduct();
		ProductCreateDto productCreateDto1 = new ProductCreateDto("test", 2, 1, BigDecimal.ONE, null);
		Throwable e = assertThrows(NotFoundException.class, () -> productService.addProduct(productCreateDto1));
		assertEquals("Category with id 2 not found.", e.getMessage());
	}

	@Test
	public void addProductWrongSupplierTest() {
		var product = generateExpectedProduct();
		ProductCreateDto productCreateDto1 = new ProductCreateDto("test", 1, 2, BigDecimal.ONE, null);
		Throwable e = assertThrows(NotFoundException.class, () -> productService.addProduct(productCreateDto1));
		assertEquals("Supplier with id 2 not found.", e.getMessage());
	}

	@Test
	public void addProductDuplicatedProductTest() {
		generateExpectedProduct();
		ProductCreateDto productCreateDto1 = new ProductCreateDto("test duplicated", 1, 1, BigDecimal.ONE, null);
		Throwable e = assertThrows(AlreadyExistsException.class, () -> productService.addProduct(productCreateDto1));
		assertEquals("Product with name test duplicated and supplier id 1 already exists", e.getMessage());
	}

	// Update product Scenario tests
	@Test
	public void updateProductSuccessfulTest() {
		var product = generateExpectedProduct();
		when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));

		ProductUpdateDto productUpdateDto = new ProductUpdateDto(product.getId(), "test", 1, 1, BigDecimal.ONE, "test update", ProductStatus.AVAILABLE);
		assertThat(productService.updateProduct(productUpdateDto), Matchers.equalTo(productMapper.toDto(product)));
	}

	// Delete product scenario tests

	@Test
	public void deleteProductSuccessfulTest() {
		var product = generateExpectedProduct();
		when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));

		assertDoesNotThrow(() -> productService.deleteProduct(product.getId()));
	}

	@Test
	public void deleteProductWrongIdTest() {
		var product = generateExpectedProduct();
		when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));
		when(productRepository.findById(not(eq(product.getId())))).thenReturn(Optional.empty());

		var id = UUID.randomUUID();

		Throwable e = assertThrows(NotFoundException.class, () -> productService.deleteProduct(id));
		assertEquals("Product with id " + id + " not found", e.getMessage());
	}

	//Attach to product scenario tests
	@Test
	public void attachProductSuccessfulTest(){
		var product = generateExpectedProduct();
		product.setAttachments(List.of());
		when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));

		var attachmentDto = new AttachmentCreateDto("gfjgfjgfh",product.getId());

		assertDoesNotThrow(()->productService.attach(attachmentDto));
	}

	//Remove attachment scenario tests
	@Test
	public void removeAttachmentSuccessfulTest() {
		var successfulId = UUID.randomUUID();
		when(attachmentRepository.findById(eq(successfulId))).thenReturn(Optional.of(new AttachmentDomain()));

		assertDoesNotThrow(() -> productService.removeAttachment(successfulId));
	}

	@Test
	public void removeAttachmentWrongIdTest() {
		var successfulId = UUID.randomUUID();
		when(attachmentRepository.findById(eq(successfulId))).thenReturn(Optional.of(new AttachmentDomain()));

		when(attachmentRepository.findById(not(eq(successfulId)))).thenReturn(Optional.empty());

		var badId = UUID.randomUUID();
		Throwable e = assertThrows(NotFoundException.class, () -> productService.removeAttachment(badId));
		assertEquals("Attachement with id " + badId + " not found", e.getMessage());
	}
}
