package vs.order_service.api.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Builder;

@Schema(
    description = "Модель ответа на запрос о получении детальной информации о заказе.")
@Builder
public record DetailResponseDto(
    UUID detailId,
    String productName,
    UUID productCode,
    int quantity,
    double price

) {

}
