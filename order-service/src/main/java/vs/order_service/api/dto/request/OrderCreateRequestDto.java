package vs.order_service.api.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель запроса на создание нового заказа.")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record OrderCreateRequestDto(

//    @Schema(description = "Имя клиента (с заглавной буквы)", example = "Иван Иванов")
    String clientName,

//    @Schema(description = "Адрес доставки (с заглавной буквы)", example = "Москва, ул. Тестовая, 77-1")
    String deliveryAddress,

//    @Schema(description = "Тип оплаты (карта, наличные)", example = "CARD")
    String paymentType,

//    @Schema(description = "Тип доставки (самовывоз, доставка до двери)", example = "DOOR_TO_DOOR")
    String deliveryType,

//    @Schema(description = "Название товара", example = "Молоко")
    String productName,

//    @Schema(description = "Количество", example = "5")
    int quantity

) {

}
