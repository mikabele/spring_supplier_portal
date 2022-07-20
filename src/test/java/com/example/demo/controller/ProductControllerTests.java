package com.example.demo.controller;

import com.example.demo.dto.AttachmentReadDto;
import com.example.demo.dto.ProductReadDto;
import com.example.demo.exception.AlreadyExistsException;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @RunWith(SpringRunner.class)
@SpringBootTest
// @WebMvcTest(ProductController.class)
public class ProductControllerTests {
  @MockBean private ProductService productService;

  @Autowired private WebApplicationContext context;
  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    mockMvc =
        MockMvcBuilders.webAppContextSetup(context)
            // .apply(springSecurity())
            .build();
  }

  @Test
  public void addProductValidationSuccessfulTest() throws Exception {

    when(productService.addProduct(any())).thenReturn(new ProductReadDto());

    mockMvc
        .perform(
            post("/api/product")
                .content(
                    "{\n"
                        + "    \"name\":\"ty\",\n"
                        + "    \"categoryId\": 1,\n"
                        + "    \"supplierId\": 1,\n"
                        + "    \"price\":2\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void addProductValidationFailedTest() throws Exception {
    mockMvc
        .perform(
            post("/api/product")
                .content(
                    "{\n"
                        + "    \"name\":\"\",\n"
                        + "    \"categoryId\": -1,\n"
                        + "    \"supplierId\": -1,\n"
                        + "    \"price\":-2\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.length()", is(4)));
  }

  @Test
  public void addProductMissingColumnTest() throws Exception {
    mockMvc
        .perform(
            post("/api/product")
                .content(
                    "{\n"
                        + "    \"name\":\"test\",\n"
                        + "    \"supplierId\": 1,\n"
                        + "    \"price\":2\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.length()", is(1)));
  }

  @Test
  public void attachToProductSuccessfulTest() throws Exception {
    when(productService.attach(any())).thenReturn(new AttachmentReadDto());

    mockMvc
        .perform(
            post("/api/product/attachment")
                .content(
                    "{\n"
                        + "    \"attachment\": \"test url\",\n"
                        + "    \"productId\":\"7f431d10-66b7-487d-8385-e3514753fe45\"\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void attachToProductDuplicatedAttachmentTest() throws Exception {
    when(productService.attach(any()))
        .thenReturn(new AttachmentReadDto())
        .thenThrow(new AlreadyExistsException("test"));

    var req =
        post("/api/product/attachment")
            .content(
                "{\n"
                    + "    \"attachment\": \"test url\",\n"
                    + "    \"productId\":\"7f431d10-66b7-487d-8385-e3514753fe45\"\n"
                    + "}")
            .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(req).andExpect(status().isOk());
    mockMvc.perform(req).andExpect(status().isBadRequest());
  }

  @Test
  public void attachToProductValidationFailedTest() throws Exception {

    mockMvc
        .perform(
            post("/api/product/attachment")
                .content(
                    "{\n"
                        + "    \"attachment\": \"\",\n"
                        + "    \"productId\":\"7f431d10-66b7-487d-8385-e3514753f\"\n"
                        + "}")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.length()", is(2)));
  }
}
