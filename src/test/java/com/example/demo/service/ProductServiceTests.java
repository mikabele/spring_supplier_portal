package com.example.demo.service;

import com.example.demo.domain.*;
import com.example.demo.dto.AttachmentCreateDto;
import com.example.demo.dto.ProductCreateDto;
import com.example.demo.dto.ProductUpdateDto;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.AttachmentMapper;
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

  @MockBean private ProductRepository productRepository;

  @MockBean private AttachmentRepository attachmentRepository;

  @MockBean private SupplierRepository supplierRepository;

  @MockBean private CategoryRepository categoryRepository;
  private ProductService productService;

  @Autowired private ProductMapper productMapper;
  @Autowired private CriteriaMapper criteriaMapper;
  @Autowired private AttachmentMapper attachmentMapper;

  @BeforeEach
  void initUseCase() {
    productService =
        new ProductService(
            productMapper,
            criteriaMapper,
            attachmentMapper,
            supplierRepository,
            categoryRepository,
            productRepository,
            attachmentRepository);
  }

  // Add product scenario tests
  @Test
  public void addProductSuccessfulTest() {

    var product = generateProduct();
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));
    when(productRepository.save(any(ProductDomain.class))).thenReturn(product);
    when(productRepository.findByNameAndSupplierId(eq("test"), eq(1))).thenReturn(Optional.empty());
    when(productRepository.findByNameAndSupplierId(not(eq("test")), any(Integer.class)))
        .thenReturn(Optional.of(new ProductDomain()));

    ProductCreateDto productCreateDto = new ProductCreateDto("test", 1, 1, BigDecimal.ONE, null);
    assertThat(
        productService.addProduct(productCreateDto),
        Matchers.equalTo(productMapper.toDto(product)));
  }

  ProductDomain generateProduct() {
    var product = new ProductDomain();
    product.setName("test");
    product.setId(UUID.randomUUID());
    var supplier = new SupplierDomain();
    supplier.setId(1);
    var category = new CategoryDomain();
    category.setId(1);
    product.setSupplier(supplier);
    product.setCategory(category);
    return product;
  }

  @Test
  public void addProductWrongCategoryTest() {
    ProductCreateDto productCreateDto1 = new ProductCreateDto("test", 2, 1, BigDecimal.ONE, null);
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));

    Throwable e =
        assertThrows(NotFoundException.class, () -> productService.addProduct(productCreateDto1));
    assertEquals("Category with id 2 not found.", e.getMessage());
  }

  @Test
  public void addProductWrongSupplierTest() {
    ProductCreateDto productCreateDto1 = new ProductCreateDto("test", 1, 2, BigDecimal.ONE, null);
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));

    Throwable e =
        assertThrows(NotFoundException.class, () -> productService.addProduct(productCreateDto1));
    assertEquals("Supplier with id 2 not found.", e.getMessage());
  }

  @Test
  public void addProductDuplicatedProductTest() {
    ProductCreateDto productCreateDto1 =
        new ProductCreateDto("test duplicated", 1, 1, BigDecimal.ONE, null);
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));
    when(productRepository.findByNameAndSupplierId(not(eq("test")), any(Integer.class)))
        .thenReturn(Optional.of(new ProductDomain()));

    Throwable e =
        assertThrows(
            AlreadyExistsException.class, () -> productService.addProduct(productCreateDto1));
    assertEquals(
        "Product with name test duplicated and supplier id 1 already exists", e.getMessage());
  }

  // Update product Scenario tests
  @Test
  public void updateProductSuccessfulTest() {
    var product = generateProduct();
    when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));
    when(productRepository.save(any(ProductDomain.class))).thenReturn(product);
    when(productRepository.findByNameAndSupplierId(not(eq("test")), any(Integer.class)))
        .thenReturn(Optional.of(new ProductDomain()));

    ProductUpdateDto productUpdateDto =
        new ProductUpdateDto(
            product.getId(), "test", 1, 1, BigDecimal.ONE, "test update", ProductStatus.AVAILABLE);
    assertThat(
        productService.updateProduct(productUpdateDto),
        Matchers.equalTo(productMapper.toDto(product)));
  }

  @Test
  public void updateProductWrongIdTest() {
    var product = generateProduct();
    when(productRepository.findById(not(eq(product.getId())))).thenReturn(Optional.empty());
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));

    var wrongId = UUID.randomUUID();

    ProductUpdateDto productUpdateDto =
        new ProductUpdateDto(
            wrongId, "test", 1, 1, BigDecimal.ONE, "test update", ProductStatus.AVAILABLE);
    Throwable e =
        assertThrows(NotFoundException.class, () -> productService.updateProduct(productUpdateDto));
    assertEquals("Product with id " + wrongId + " not found", e.getMessage());
  }

  @Test
  public void updateProductDuplicatedNameTest() {
    var product = generateProduct();

    var duplicatedProduct = generateProduct();

    when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));
    when(categoryRepository.findById(1)).thenReturn(Optional.of(new CategoryDomain()));
    when(supplierRepository.findById(1)).thenReturn(Optional.of(new SupplierDomain()));
    when(productRepository.findByNameAndSupplierId(any(), any(Integer.class)))
        .thenReturn(Optional.of(duplicatedProduct));

    ProductUpdateDto productUpdateDto =
        new ProductUpdateDto(
            product.getId(),
            "test duplicated",
            1,
            1,
            BigDecimal.ONE,
            "test update",
            ProductStatus.AVAILABLE);

    Throwable e =
        assertThrows(
            AlreadyExistsException.class, () -> productService.updateProduct(productUpdateDto));
    assertEquals(
        "Product with name test duplicated and supplier id 1 already exists", e.getMessage());
  }

  // Delete product scenario tests

  @Test
  public void deleteProductSuccessfulTest() {
    var product = generateProduct();
    when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));

    assertDoesNotThrow(() -> productService.deleteProduct(product.getId()));
  }

  @Test
  public void deleteProductWrongIdTest() {
    var product = generateProduct();
    when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));
    when(productRepository.findById(not(eq(product.getId())))).thenReturn(Optional.empty());

    var id = UUID.randomUUID();

    Throwable e = assertThrows(NotFoundException.class, () -> productService.deleteProduct(id));
    assertEquals("Product with id " + id + " not found", e.getMessage());
  }

  // Attach to product scenario tests
  @Test
  public void attachProductSuccessfulTest() {
    var product = generateProduct();
    product.setAttachments(List.of());
    when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));

    var attachmentDto = new AttachmentCreateDto("test url", product.getId());

    assertDoesNotThrow(() -> productService.attach(attachmentDto));
  }

  @Test
  public void attachToProductWrongProductIdTest() {
    var product = generateProduct();
    product.setAttachments(List.of());
    when(productRepository.findById(not(eq(product.getId())))).thenReturn(Optional.empty());

    var wrongId = UUID.randomUUID();
    var attachmentDto = new AttachmentCreateDto("test url", wrongId);

    Throwable e = assertThrows(NotFoundException.class, () -> productService.attach(attachmentDto));
    assertEquals("Product with id " + wrongId + " not found", e.getMessage());
  }

  @Test
  public void attachToProductDuplicatedAttachmentTest() {
    var product = generateProduct();
    var attachment = new AttachmentDomain();
    attachment.setAttachment("test url");
    attachment.setProduct(product);
    product.setAttachments(List.of(attachment));
    when(productRepository.findById(eq(product.getId()))).thenReturn(Optional.of(product));

    var attachmentDto = new AttachmentCreateDto("test url", product.getId());

    Throwable e =
        assertThrows(AlreadyExistsException.class, () -> productService.attach(attachmentDto));
    assertEquals(
        "Attachment with url "
            + attachment.getAttachment()
            + " and product id "
            + attachment.getProduct().getId()
            + " already exists",
        e.getMessage());
  }

  // Remove attachment scenario tests
  @Test
  public void removeAttachmentSuccessfulTest() {
    var successfulId = UUID.randomUUID();
    when(attachmentRepository.findById(eq(successfulId)))
        .thenReturn(Optional.of(new AttachmentDomain()));

    assertDoesNotThrow(() -> productService.removeAttachment(successfulId));
  }

  @Test
  public void removeAttachmentWrongIdTest() {
    var successfulId = UUID.randomUUID();
    when(attachmentRepository.findById(eq(successfulId)))
        .thenReturn(Optional.of(new AttachmentDomain()));
    when(attachmentRepository.findById(not(eq(successfulId)))).thenReturn(Optional.empty());

    var badId = UUID.randomUUID();
    Throwable e =
        assertThrows(NotFoundException.class, () -> productService.removeAttachment(badId));
    assertEquals("Attachment with id " + badId + " not found", e.getMessage());
  }
}
