package vs.product_service.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Модель ответа по факту удаления продукта.")
public record DeleteProductResponseDto(
    UUID productId,
    String productName

) {

}
