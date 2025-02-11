package vs.order_service.api.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.ErrorResponse;
import vs.order_service.api.dto.responce.OrderResponseDto;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@ApiResponses(value = {

    @ApiResponse(responseCode = "200",
        description = "Ok",
        content = @Content(mediaType = "application/json",
            examples = @ExampleObject(
                value = "[{"
                    + "\"orderId\": \"6c497533-a6e3-400a-bedc-8d1729def82e\","
                    + "\"orderNumber\": \"0418720250231\","
                    + "\"totalAmount\": 110.0,"
                    + "\"orderDate\": \"2025-02-31\","
                    + "\"clientName\": \"Ivan Petrov\","
                    + "\"deliveryAddress\": \"Москва, какой-то тестовый адрес\","
                    + "\"paymentType\": \"CARD\","
                    + "\"deliveryType\": \"DOOR_TO_DOOR\"" +
                    "}," +
                    "{"
                    + "\"orderId\": \"aa134445-4640-4892-8ba2-f9d102b882ae\","
                    + "\"orderNumber\": \"0999420250127\","
                    + "\"totalAmount\": 105.0,"
                    + "\"orderDate\": \"2025-02-30\","
                    + "\"clientName\": \"Sveta Ivanova\","
                    + "\"deliveryAddress\": \"Москва, какой-то тестовый адрес\","
                    + "\"paymentType\": \"CARD\","
                    + "\"deliveryType\": \"DOOR_TO_DOOR\"" +
                    "}]"))),

    @ApiResponse(responseCode = "404",
        description = "Not Found",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                value = "{\"uri\": \"/api/order-service/orders/search\","
                    + "\"type\": \"NotFoundException\","
                    + "\"message\": \"No orders by date and total amount founded\","
                    + "\"timestamp\": \"2025-02-31 05:01:843\"}")))
})

public @interface SearchOrdersByDateAndTotalAmountApiResponsesOperation {

}
