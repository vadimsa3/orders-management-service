package vs.product_service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.reqest.ProductUpdateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;
import vs.product_service.db.entity.ProductEntity;

public class Fixtures {

  public static UUID productId = UUID.fromString("7b5c7537-51b4-4fd8-8e1b-171931c6125b");

  public static UUID productId1 = UUID.randomUUID();

  public static UUID productId2 = UUID.randomUUID();

  public static String productName = "TEST-PRODUCT";

  public static double price = 50.0;

  public static double newPrice = 100.0;

  public static ProductEntity getProductEntity() {
    return ProductEntity.builder()
        .productId(productId)
        .productName(productName)
        .price(price)
        .build();
  }

  public static ProductCreateRequestDto getProductCreateRequestDto() {
    return new ProductCreateRequestDto(productName, price);
  }

  public static ProductUpdateRequestDto getProductUpdateRequestDto() {
    return new ProductUpdateRequestDto(productName, newPrice);
  }

  public static CreateProductResponseDto getCreateProductResponseDto() {
    return new CreateProductResponseDto(productId, productName, price);
  }

  public static ProductResponseDto getProductResponseDto() {
    return new ProductResponseDto(productId, productName, price);
  }

  public static UpdateProductResponseDto getUpdateProductResponseDto() {
    return new UpdateProductResponseDto(productName, newPrice);
  }

  public static DeleteProductResponseDto getDeleteProductResponseDto() {
    return new DeleteProductResponseDto(productId, productName);
  }

  public static List<ProductEntity> getProductEntityList() {
    ProductEntity productEntity1 =
        new ProductEntity(productId1, productName + 1, price);
    ProductEntity productEntity2 =
        new ProductEntity(productId2, productName + 2, price);
    List<ProductEntity> productEntityList = new ArrayList<>();
    productEntityList.add(productEntity1);
    productEntityList.add(productEntity2);
    return productEntityList;
  }

  public static List<ProductResponseDto> getProductResponseDtoList() {
    ProductResponseDto productResponseDto1 =
        new ProductResponseDto(productId1, productName + 1, price);
    ProductResponseDto productResponseDto2 =
        new ProductResponseDto(productId2, productName + 2, price);
    List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
    productResponseDtoList.add(productResponseDto1);
    productResponseDtoList.add(productResponseDto2);
    return productResponseDtoList;
  }


}
