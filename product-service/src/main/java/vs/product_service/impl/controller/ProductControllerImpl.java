package vs.product_service.impl.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vs.product_service.api.controller.ProductController;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.reqest.ProductUpdateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;
import vs.product_service.impl.service.ProductService;

@RestController
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {

  private final ProductService productService;

  @Override
  public ResponseEntity<CreateProductResponseDto> createProduct(
      ProductCreateRequestDto productCreateRequestDto) {
    CreateProductResponseDto createProductResponseDto =
        productService.createProduct(productCreateRequestDto);
    return new ResponseEntity<>(createProductResponseDto, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<ProductResponseDto> getProduct(
      @Parameter(description = "Наименование продукта", example = "Молоко") String productName) {
    ProductResponseDto productResponseDto = productService.getProductByName(productName);
    return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<ProductResponseDto>> getAllProductsPageable(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int limit) {
    var products = productService.getAllProducts(page, limit);
    return ResponseEntity.ok(products);
  }

  @Override
  public ResponseEntity<UpdateProductResponseDto> updateProduct(
      @Parameter(description = "Id продукта", example = "1cbfce9a-fa90-4598-a601-c327d793f3e5")
      UUID productId, ProductUpdateRequestDto productUpdateRequestDto) {
    UpdateProductResponseDto updateProductResponseDto =
        productService.updateProduct(productId, productUpdateRequestDto);
    return new ResponseEntity<>(updateProductResponseDto, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<DeleteProductResponseDto> deleteProduct(
      @Parameter(description = "Id продукта", example = "1cbfce9a-fa90-4598-a601-c327d793f3e5")
      UUID productId) {
    DeleteProductResponseDto deleteProductResponseDto = productService.deleteProduct(productId);
    return ResponseEntity.ok(deleteProductResponseDto);
  }

}
