package vs.product_service.api.annotation;

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
import vs.product_service.api.response.CreateProductResponseDto;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@ApiResponses(value = {

    @ApiResponse(responseCode = "201",
        description = "Created",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateProductResponseDto.class),
            examples = @ExampleObject(
                value = "{\"productId\": \"d2201ec5-4079-4232-9195-b8a09de53fe2\","
                    + "\"productName\": \"Чай\","
                    + "\"price\": 70.0}"))),

    @ApiResponse(responseCode = "400",
        description = "Bad Request",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                value = "{\"uri\": \"/api/product-service/create\","
                    + "\"type\": \"BadRequestException\","
                    + "\"message\": \"Product with name Чай is already exists\","
                    + "\"timestamp\": \"2025-02-31 05:01:843\"}")))
})

public @interface CreateProductApiResponsesOperation {

}
