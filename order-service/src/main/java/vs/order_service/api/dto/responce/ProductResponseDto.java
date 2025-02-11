package vs.order_service.api.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Builder;

@Schema(description = "Модель ответа на запрос о получении информации о продукте.")
@Builder
public record ProductResponseDto(
    UUID productId,
    String productName,
    double price

) {


}
