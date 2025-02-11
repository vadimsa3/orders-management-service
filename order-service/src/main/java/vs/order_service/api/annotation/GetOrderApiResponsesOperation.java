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
            schema = @Schema(implementation = OrderResponseDto.class),
            examples = @ExampleObject(
                value = "{\"orderId\": \"fd956617-b536-4e10-80b9-6f8c3102a985\","
                    + "\"orderNumber\": \"0418720250231\","
                    + "\"totalAmount\": 110.0,"
                    + "\"orderDate\": \"2025-02-31\","
                    + "\"clientName\": \"Ivan Petrov\","
                    + "\"deliveryAddress\": \"Москва, какой-то тестовый адрес\","
                    + "\"paymentType\": \"CARD\","
                    + "\"deliveryType\": \"DOOR_TO_DOOR\"}"))),

    @ApiResponse(responseCode = "404",
        description = "Not Found",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                value = "{\"uri\": \"/api/order-service/orders/5711420250127\","
                    + "\"type\": \"NotFoundException\","
                    + "\"message\": \"Order with number 5711420250127 not found\","
                    + "\"timestamp\": \"2025-02-31 05:01:843\"}")))
})

public @interface GetOrderApiResponsesOperation {

}