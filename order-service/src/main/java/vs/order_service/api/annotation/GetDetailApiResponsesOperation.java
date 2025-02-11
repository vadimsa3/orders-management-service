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
import vs.order_service.api.dto.responce.DetailResponseDto;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@ApiResponses(value = {

    @ApiResponse(responseCode = "200",
        description = "Ok",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = DetailResponseDto.class),
            examples = @ExampleObject(
                value = "{\"detailId\": \"d2201ec5-4079-4232-9195-b8a09de53fe2\","
                    + "\"productName\": \"Чай\","
                    + "\"productCode\": \"1cbfce9a-fa90-4598-a601-0c327d793f3e\","
                    + "\"quantity\": 5,"
                    + "\"price\": 70.0}"))),

    @ApiResponse(responseCode = "404",
        description = "Not Found",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class),
            examples = @ExampleObject(
                value = "{\"uri\": \"/api/order-service/orders/detail/d2201ec5-4079-4232-9195-b8a09de53fe2\","
                    + "\"type\": \"NotFoundException\","
                    + "\"message\": \"Order with Id d2201ec5-4079-4232-9195-b8a09de53fe2 not found\","
                    + "\"timestamp\": \"2025-02-31 02:00:547\"}")))
})

public @interface GetDetailApiResponsesOperation {

}
