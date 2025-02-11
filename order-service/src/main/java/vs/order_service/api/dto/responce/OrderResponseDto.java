package vs.order_service.api.dto.responce;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;

@Schema(description = "Модель ответа на запрос о получении информации о заказе.")
@Builder
public record OrderResponseDto(
    UUID orderId,
    String orderNumber,
    double totalAmount,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate orderDate,
    String clientName,
    String deliveryAddress,
    String paymentType,
    String deliveryType

) {


}
