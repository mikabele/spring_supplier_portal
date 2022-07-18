package com.example.demo;

import com.example.demo.domain.CategoryDomain;
import com.example.demo.domain.ProductDomain;
import com.example.demo.domain.SupplierDomain;
import com.example.demo.dto.ProductCreateDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.*;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.SupplierRepository;
import com.example.demo.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
public class ProductServiceTests {


	@MockBean
	private ProductRepository productRepository;

	@MockBean
	private AttachmentRepository attachmentRepository;

	@MockBean
	private SupplierRepository supplierRepository;

	@MockBean
	private CategoryRepository categoryRepository;
	//@InjectMocks
	private ProductService productService;

	@Autowired
	private CategoryMapper categoryMapper;// = new CategoryMapperImpl();
	@Autowired
	private ProductMapper productMapper;// = new ProductMapperImpl();
	@Autowired
	private CriteriaMapper criteriaMapper;// = new CriteriaMapperImpl();
	@Autowired
	private AttachmentMapper attachmentMapper;// = new AttachmentMapperImpl();


	@BeforeEach
	void initUseCase() {
		productService = new ProductService(productMapper, criteriaMapper, attachmentMapper, supplierRepository,
				categoryRepository, productRepository, attachmentRepository);
	}

	ProductDomain generateExpectedProduct() {
		var product = new ProductDomain();
		product.setName("test");
		var supplier = new SupplierDomain();
		supplier.setId(1);
		var category = new CategoryDomain();
		category.setId(1);
		product.setSupplier(supplier);
		product.setCategory(category);
		when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
		when(supplierRepository.findById(1)).thenReturn(Optional.of(supplier));
		when(productRepository.save(any(ProductDomain.class))).thenReturn(product);
		return product;
	}

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


}
