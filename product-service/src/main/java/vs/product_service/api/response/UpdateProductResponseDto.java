package vs.product_service.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Модель ответа по факту изменения данных о продукте.")
@Builder
public record UpdateProductResponseDto(
    String productName,
    double price

) {

}
