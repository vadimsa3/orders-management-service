package vs.product_service.api.reqest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Модель запроса на добавление нового продукта.")
public record ProductCreateRequestDto(

    @Schema(description = "Наименование продукта (с заглавной буквы)", example = "Молоко")
    String productName,

    @Schema(description = "Цена продукта", example = "10.5")
    double price

) {

}
