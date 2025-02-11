package vs.product_service.api.reqest;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Модель запроса на изменение продукта.")
public record ProductUpdateRequestDto(

    @Schema(description = "Новое наименование продукта (с заглавной буквы)", example = "Масло")
    @NotBlank(message = "Product name cannot be empty")
    String productName,

    @Schema(description = "Новая цена продукта", example = "15.5")
    @NotBlank(message = "Price cannot be empty")
    double price

) {

}
