package vs.product_service.impl.service;

import java.util.List;
import java.util.UUID;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.reqest.ProductUpdateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;

public interface ProductService {

  CreateProductResponseDto createProduct(ProductCreateRequestDto productCreateRequestDto);

  ProductResponseDto getProductByName(String productName);

  List<ProductResponseDto> getAllProducts(int page, int size);

  UpdateProductResponseDto updateProduct(
      UUID productId, ProductUpdateRequestDto productUpdateRequestDto);

  DeleteProductResponseDto deleteProduct(UUID productId);

}
