package vs.product_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vs.product_service.util.Fixtures.getCreateProductResponseDto;
import static vs.product_service.util.Fixtures.getDeleteProductResponseDto;
import static vs.product_service.util.Fixtures.getProductCreateRequestDto;
import static vs.product_service.util.Fixtures.getProductEntity;
import static vs.product_service.util.Fixtures.getProductEntityList;
import static vs.product_service.util.Fixtures.getProductResponseDto;
import static vs.product_service.util.Fixtures.getProductResponseDtoList;
import static vs.product_service.util.Fixtures.getProductUpdateRequestDto;
import static vs.product_service.util.Fixtures.getUpdateProductResponseDto;
import static vs.product_service.util.Fixtures.productId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import vs.exception_handler_starter.exception.AppException;
import vs.exception_handler_starter.exception.EnumException;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.reqest.ProductUpdateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;
import vs.product_service.db.entity.ProductEntity;
import vs.product_service.db.repository.ProductRepository;
import vs.product_service.impl.mapper.ProductMapper;
import vs.product_service.impl.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "vs.exception_handler_starter.handler")
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductServiceImpl productService;

  @Test
  void testCreateProductShouldReturnCreateProductResponseDto() {
    ProductCreateRequestDto productCreateRequestDto = getProductCreateRequestDto();
    CreateProductResponseDto createProductResponseDto = getCreateProductResponseDto();
    ProductEntity productEntity = getProductEntity();

    when(productMapper.mapToProductEntity(productCreateRequestDto)).thenReturn(productEntity);
    when(productMapper.mapToCreateProductResponseDto(productEntity))
        .thenReturn(createProductResponseDto);
    when(productRepository.save(productEntity)).thenReturn(productEntity);

    CreateProductResponseDto result = productService.createProduct(productCreateRequestDto);

    assertNotNull(result);
    assertEquals(productCreateRequestDto.productName(), result.productName());

    verify(productRepository, times(1)).save(productEntity);
    verify(productMapper).mapToProductEntity(productCreateRequestDto);
    verify(productRepository).existsByProductName(productEntity.getProductName());
    verify(productRepository).save(productEntity);
    verify(productMapper).mapToCreateProductResponseDto(productEntity);
  }

  @Test
  void testCreateProductShouldThrowExceptionWhenProductExists() {
    ProductCreateRequestDto productCreateRequestDto = getProductCreateRequestDto();
    ProductEntity productEntity = getProductEntity();

    when(productMapper.mapToProductEntity(productCreateRequestDto)).thenReturn(productEntity);
    when(productRepository.existsByProductName(productEntity.getProductName())).thenReturn(true);

    AppException exception = assertThrows(AppException.class, () -> {
      productService.createProduct(productCreateRequestDto);
    });

    assertEquals(EnumException.BAD_REQUEST, exception.getEnumInterface());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    assertEquals("Product with name TEST-PRODUCT is already exists",
        exception.getErrorMessage());

    verify(productMapper).mapToProductEntity(productCreateRequestDto);
    verify(productRepository).existsByProductName(productEntity.getProductName());
    verify(productRepository, never()).save(any(ProductEntity.class));
  }

  @Test
  void testGetProductByNameShouldReturnProductResponseDto() {
    ProductResponseDto productResponseDto = getProductResponseDto();
    ProductEntity productEntity = getProductEntity();
    String productName = productResponseDto.productName();

    when(productMapper.mapToProductResponseDto(productEntity)).thenReturn(productResponseDto);
    when(productRepository.findByProductName(productName)).thenReturn(Optional.of(productEntity));

    ProductResponseDto result = productService.getProductByName(productName);

    assertNotNull(result);
    assertEquals(productResponseDto.productName(), result.productName());
    assertEquals(productResponseDto.price(), result.price());

    verify(productMapper).mapToProductResponseDto(productEntity);
    verify(productRepository).findByProductName(productName);
  }

  @Test
  void testGetProductByNameShouldThrowExceptionWhenNotFound() {
    ProductResponseDto productResponseDto = getProductResponseDto();
    String productName = productResponseDto.productName();

    when(productRepository.findByProductName(productName)).thenReturn(Optional.empty());

    AppException exception = assertThrows(AppException.class, () -> {
      productService.getProductByName(productName);
    });

    assertEquals(EnumException.BAD_REQUEST, exception.getEnumInterface());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    assertEquals("Product with name TEST-PRODUCT not found",
        exception.getErrorMessage());

    verify(productRepository, times(1)).findByProductName(productName);
  }

  @Test
  void testGetAllProductsShouldReturnListProductsResponseDto() {
    List<ProductEntity> productEntities = getProductEntityList();
    Page<ProductEntity> page = new PageImpl<>(productEntities);

    when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

    when(productMapper.mapToProductResponseList(productEntities))
        .thenReturn(getProductResponseDtoList());

    List<ProductResponseDto> response = productService.getAllProducts(0, 10);

    assertNotNull(response);
    assertEquals(2, response.size());
    assertEquals("TEST-PRODUCT1", response.get(0).productName());
    assertEquals("TEST-PRODUCT2", response.get(1).productName());

    verify(productRepository).findAll(PageRequest.of(0, 10));
    verify(productMapper).mapToProductResponseList(productEntities);
  }

  @Test
  void testGetAllProductsShouldThrowExceptionWhenNotFound() {
    Page<ProductEntity> emptyPage = new PageImpl<>(List.of());

    when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(emptyPage);

    AppException exception = assertThrows(AppException.class, () -> {
      productService.getAllProducts(0, 10);
    });

    assertEquals(EnumException.NOT_FOUND, exception.getEnumInterface());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    assertEquals("No products found", exception.getErrorMessage());

    verify(productRepository).findAll(PageRequest.of(0, 10));
    verify(productMapper, never()).mapToProductResponseList(anyList());
  }

  @Test
  void testUpdateProductShouldReturnUpdateProductResponseDto() {
    ProductUpdateRequestDto productUpdateRequestDto = getProductUpdateRequestDto();
    UpdateProductResponseDto updateProductResponseDto = getUpdateProductResponseDto();
    ProductEntity productEntity = getProductEntity();
    UUID productId = productEntity.getProductId();

    when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
    when(productMapper.mapToUpdateProductResponseDto(productEntity))
        .thenReturn(updateProductResponseDto);

    UpdateProductResponseDto result = productService.updateProduct(productId,
        productUpdateRequestDto);

    assertNotNull(result);
    assertEquals(updateProductResponseDto.productName(), result.productName());
    assertEquals(updateProductResponseDto.price(), result.price());

    verify(productMapper).mapToUpdateProductResponseDto(productEntity);
    verify(productRepository).findById(productId);
    verify(productRepository).save(productEntity);
  }

  @Test
  void testUpdateProductShouldThrowExceptionWhenBadRequest() {
    ProductUpdateRequestDto productUpdateRequestDto = getProductUpdateRequestDto();
    ProductEntity productEntity = getProductEntity();
    UUID productId = productEntity.getProductId();

    when(productRepository.findById(any())).thenReturn(Optional.empty());

    AppException exception = assertThrows(AppException.class, () -> {
      productService.updateProduct(productId, productUpdateRequestDto);
    });

    assertEquals(EnumException.BAD_REQUEST, exception.getEnumInterface());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    assertEquals(
        String.format("Product with ID %s not found", productId),
        exception.getErrorMessage());

    verify(productRepository, times(1)).findById(productId);
    verify(productMapper, never()).mapToUpdateProductResponseDto(any());
  }

  @Test
  void testDeleteProductShouldReturnDeleteProductResponseDto() {
    DeleteProductResponseDto deleteProductResponseDto = getDeleteProductResponseDto();
    ProductEntity productEntity = getProductEntity();

    when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
    when(productMapper.mapToDeleteProductResponseDto(productEntity))
        .thenReturn(deleteProductResponseDto);

    DeleteProductResponseDto result = productService.deleteProduct(productId);

    assertNotNull(result);
    assertEquals(deleteProductResponseDto.productName(), result.productName());
    assertEquals(deleteProductResponseDto.productId(), result.productId());

    verify(productRepository, times(1)).findById(productId);
    verify(productMapper).mapToDeleteProductResponseDto(productEntity);
  }

  @Test
  void testDeleteProductShouldThrowExceptionWhenBadRequest() {
    ProductEntity productEntity = getProductEntity();
    UUID productId = productEntity.getProductId();

    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    AppException exception = assertThrows(AppException.class, () -> {
      productService.deleteProduct(productId);
    });

    assertEquals(EnumException.BAD_REQUEST, exception.getEnumInterface());
    assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
    assertEquals(
        String.format("Product with ID %s not found", productId),
        exception.getErrorMessage());

    verify(productRepository, times(1)).findById(productId);
    verify(productMapper, never()).mapToDeleteProductResponseDto(any());
  }


}
