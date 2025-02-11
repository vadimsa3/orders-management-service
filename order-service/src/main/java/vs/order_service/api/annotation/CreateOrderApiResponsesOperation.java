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
import vs.order_service.api.dto.responce.CreateOrderResponseDto;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@ApiResponses(value = {

    @ApiResponse(responseCode = "201",
        description = "Created",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateOrderResponseDto.class),
            examples = @ExampleObject(
                value = "{\"order_id\": \"fd956617-b536-4e10-80b9-6f8c3102a985\","
                    + "\"order_number\": \"0418720250231\","
                    + "\"order_date\": \"2025-02-31\","
                    + "\"delivery_address\": \"Москва, какой-то тестовый адрес\","
                    + "\"payment_type\": \"CARD\","
                    + "\"delivery_type\": \"DOOR_TO_DOOR\"}"))),
})

public @interface CreateOrderApiResponsesOperation {

}
