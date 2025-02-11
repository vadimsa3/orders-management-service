package vs.product_service.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Модель ответа по факту добавления нового продукта.")
public record CreateProductResponseDto(
    UUID productId,
    String productName,
    double price

) {

}
