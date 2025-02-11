package vs.product_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.reqest.ProductUpdateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;
import vs.product_service.impl.controller.ProductControllerImpl;
import vs.product_service.impl.service.ProductService;

@ExtendWith(MockitoExtension.class)
class ProductControllerImplTest {

  @Mock
  private ProductService productService;

  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @InjectMocks
  private ProductControllerImpl productController;

  private String productName;

  private double productPrice;

  @BeforeEach
  public void init() {
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    productName = "TEST-PRODUCT";
    productPrice = 100.0;
  }

  @Test
  void testCreateProductShouldReturnCreated() throws Exception {
    ProductCreateRequestDto requestDto = new ProductCreateRequestDto(
        productName, productPrice);

    CreateProductResponseDto responseDto = new CreateProductResponseDto(
        UUID.randomUUID(), productName, productPrice);

    when(productService.createProduct(requestDto)).thenReturn(responseDto);

    mockMvc.perform(post("/api/product-service/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

    Assertions.assertDoesNotThrow(() -> productController.createProduct(requestDto));
  }

  @Test
  void testGetProductShouldReturnOk() throws Exception {
    ProductResponseDto responseDto = new ProductResponseDto(
        UUID.randomUUID(), productName, productPrice);

    when(productService.getProductByName(productName)).thenReturn(responseDto);

    mockMvc.perform(get("/api/product-service/products/{productName}", productName)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

    verify(productService, times(1)).getProductByName(productName);
  }

  @Test
  void testGetAllProductsPageableShouldReturnOk() throws Exception {
    UUID product1Id = UUID.fromString("1690d7dd-6d80-47cb-bde5-f5e11e65e95e");
    UUID product2Id = UUID.fromString("7b5c7537-51b4-4fd8-8e1b-171931c6125b");
    ProductResponseDto product1 =
        new ProductResponseDto(product1Id, "Молоко", 10.5);
    ProductResponseDto product2 =
        new ProductResponseDto(product2Id, "Масло", 11.0);
    List<ProductResponseDto> products = Arrays.asList(product1, product2);

    when(productService.getAllProducts(0, 10)).thenReturn(products);

    mockMvc.perform(get("/api/product-service/?page=0&limit=10")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].productId")
            .value("1690d7dd-6d80-47cb-bde5-f5e11e65e95e"))
        .andExpect(jsonPath("$[0].productName").value("Молоко"))
        .andExpect(jsonPath("$[0].price").value(10.5))
        .andExpect(jsonPath("$[1].productId")
            .value("7b5c7537-51b4-4fd8-8e1b-171931c6125b"))
        .andExpect(jsonPath("$[1].productName").value("Масло"))
        .andExpect(jsonPath("$[1].price").value(11.0));

    verify(productService, times(1)).getAllProducts(0, 10);
  }

  @Test
  void testUpdateProductShouldReturnOk() throws Exception {
    UUID productId = UUID.fromString("1690d7dd-6d80-47cb-bde5-f5e11e65e95e");
    String updatedProductName = "Updated Product";
    ProductUpdateRequestDto updateRequest =
        new ProductUpdateRequestDto(updatedProductName, 15.0);
    UpdateProductResponseDto responseDto =
        new UpdateProductResponseDto(updatedProductName, 20.0);

    when(productService.updateProduct(eq(productId), any(ProductUpdateRequestDto.class)))
        .thenReturn(responseDto);

    mockMvc.perform(put("/api/product-service/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productName").value("Updated Product"))
        .andExpect(jsonPath("$.price").value(20.0));

    verify(productService, times(1))
        .updateProduct(eq(productId), any(ProductUpdateRequestDto.class));
  }

  @Test
  void testDeleteProductShouldReturnOk() throws Exception {
    UUID productId = UUID.randomUUID();
    DeleteProductResponseDto responseDto = new DeleteProductResponseDto(productId,
        "Deleted Product");

    when(productService.deleteProduct(productId)).thenReturn(responseDto);

    mockMvc.perform(delete("/api/product-service/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(productId.toString()))
        .andExpect(jsonPath("$.productName").value("Deleted Product"));

    verify(productService, times(1)).deleteProduct(productId);
  }


}
