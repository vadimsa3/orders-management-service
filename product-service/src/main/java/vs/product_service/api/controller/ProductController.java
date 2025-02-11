package vs.product_service.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vs.product_service.api.annotation.CreateProductApiResponsesOperation;
import vs.product_service.api.annotation.DeleteProductApiResponsesOperation;
import vs.product_service.api.annotation.GetAllProductApiResponsesOperation;
import vs.product_service.api.annotation.GetProductApiResponsesOperation;
import vs.product_service.api.annotation.UpdateProductApiResponsesOperation;
import vs.product_service.api.reqest.ProductCreateRequestDto;
import vs.product_service.api.reqest.ProductUpdateRequestDto;
import vs.product_service.api.response.CreateProductResponseDto;
import vs.product_service.api.response.DeleteProductResponseDto;
import vs.product_service.api.response.ProductResponseDto;
import vs.product_service.api.response.UpdateProductResponseDto;

@Tag(name = "Работа с продуктами")
@RequestMapping("/api/product-service/")
public interface ProductController {

  @Operation(summary = "Создание нового продукта.",
      description = "Предназначен для добавления нового продукта.")
  @CreateProductApiResponsesOperation
  @PostMapping("/create")
  ResponseEntity<CreateProductResponseDto> createProduct(
      @RequestBody ProductCreateRequestDto productCreateRequestDto);

  @Operation(summary = "Получение информации о продукте.",
      description = "Предназначен для получения информации о продукте по его имени.")
  @GetProductApiResponsesOperation
  @GetMapping("/products/{productName}")
  ResponseEntity<ProductResponseDto> getProduct(
      @PathVariable String productName);

  @Operation(summary = "Получение информации о всех продуктах.",
      description = "Предназначен для получения информации о всех имеющихся продуктах.")
  @GetAllProductApiResponsesOperation
  @GetMapping("/")
  ResponseEntity<List<ProductResponseDto>> getAllProductsPageable(
      @PathParam("page") int page, @PathParam("limit") int limit);

  @Operation(summary = "Обновление информации о продукте.",
      description = "Предназначен для обновления данных по продукту (поиск по Id продукта).")
  @UpdateProductApiResponsesOperation
  @PutMapping("/{productId}")
  ResponseEntity<UpdateProductResponseDto> updateProduct(
      @PathVariable UUID productId,
      @RequestBody ProductUpdateRequestDto productUpdateRequestDto);

  @Operation(summary = "Удаление продукта.",
      description = "Предназначен для удаления продукта (поиск по Id продукта).")
  @DeleteProductApiResponsesOperation
  @DeleteMapping("/{productId}")
  ResponseEntity<DeleteProductResponseDto> deleteProduct(
      @PathVariable("productId") UUID productId);


}
