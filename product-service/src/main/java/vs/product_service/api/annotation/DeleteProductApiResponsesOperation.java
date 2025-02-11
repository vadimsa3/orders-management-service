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

    @ApiResponse(responseCode = "200",
        description = "Ok",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateProductResponseDto.class),
            examples = @ExampleObject(
                value = "{\"productId\": \"eae267bd-ac02-4b6c-b971-88cb1cd446d7\","
                    + "\"productName\": \"Сыр\"}"))),

    @ApiResponse(responseCode = "400",
        description = "Bad Request",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                value = "{\"uri\": \"/api/product-service/1cbfce9a-fa90-4598-a601-c327d793f3e\","
                    + "\"type\": \"BadRequestException\","
                    + "\"message\": \"Product with ID 1cbfce9a-fa90-4598-a601-0c327d793f3e not found\","
                    + "\"timestamp\": \"2025-02-31 05:01:843\"}")))
})

public @interface DeleteProductApiResponsesOperation {

}
