package vs.product_service.impl.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

  private final ProductMapper productMapper;

  private final ProductRepository productRepository;

  @Override
  public CreateProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
    ProductEntity productEntity = productMapper.mapToProductEntity(productCreateRequestDto);

    if (productRepository.existsByProductName(productEntity.getProductName())) {
      log.error("Product with name {} is already exists", productCreateRequestDto.productName());
      throw new AppException(EnumException.BAD_REQUEST,
          String.format("Product with name %s is already exists",
              productCreateRequestDto.productName()));
    }

    productRepository.save(productEntity);
    log.info("Product with name {} created", productEntity.getProductName());
    return productMapper.mapToCreateProductResponseDto(productEntity);
  }


  @Override
  public ProductResponseDto getProductByName(String productName) {
    ProductEntity productEntity = productRepository.findByProductName(productName)
        .orElseThrow(() ->
            new AppException(EnumException.BAD_REQUEST,
                String.format("Product with name %s not found", productName)));
    log.info("Product with name {} founded", productEntity.getProductName());
    return productMapper.mapToProductResponseDto(productEntity);
  }

  @Override
  public List<ProductResponseDto> getAllProducts(int page, int size) {
    List<ProductEntity> productEntities =
        productRepository.findAll(PageRequest.of(page, size)).getContent();
    if (productEntities.isEmpty()) {
      throw new AppException(EnumException.NOT_FOUND, HttpStatus.NOT_FOUND, "No products found");
    }
    log.info("Products founded");
    return productMapper.mapToProductResponseList(productEntities);
  }

  @Override
  public UpdateProductResponseDto updateProduct(
      UUID productId, ProductUpdateRequestDto productUpdateRequestDto) {
    ProductEntity productEntity = productRepository.findById(productId)
        .orElseThrow(() ->
            new AppException(EnumException.BAD_REQUEST,
                String.format("Product with ID %s not found", productId)));
    productEntity.setProductName(productUpdateRequestDto.productName());
    productEntity.setPrice(productUpdateRequestDto.price());
    productRepository.save(productEntity);
    log.info("Product with id {} updated", productEntity.getProductId());
    return productMapper.mapToUpdateProductResponseDto(productEntity);
  }

  @Override
  public DeleteProductResponseDto deleteProduct(UUID productId) {
    ProductEntity productEntity = productRepository.findById(productId)
        .orElseThrow(() ->
            new AppException(EnumException.BAD_REQUEST,
                String.format("Product with ID %s not found", productId)));
    productRepository.delete(productEntity);
    log.info("Product with name {} deleted", productEntity.getProductName());
    return productMapper.mapToDeleteProductResponseDto(productEntity);
  }


}
