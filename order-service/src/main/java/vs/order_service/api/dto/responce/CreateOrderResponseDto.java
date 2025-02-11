package vs.order_service.api.dto.responce;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Модель ответа на запрос на создание нового заказа.")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateOrderResponseDto(

    UUID orderId,
    String orderNumber,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate orderDate,
    String clientName,
    String deliveryAddress,
    String paymentType,
    String deliveryType

) {

}
