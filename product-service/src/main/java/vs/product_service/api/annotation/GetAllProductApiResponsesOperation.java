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
import vs.product_service.api.response.CreateProductResponseDto;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@ApiResponses(value = {

    @ApiResponse(responseCode = "200",
        description = "Ok",
        content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = CreateProductResponseDto.class),
            examples =
            @ExampleObject(
                value = "[{" +
                    "\"productId\": \"d2201ec5-4079-4232-9195-b8a09de53fe2\","
                    + "\"productName\": \"Чай\","
                    + "\"price\": 70.0" +
                    "}," +
                    "{" +
                    "\"productId\": \"d11d2c9e-698b-9873-ae96-8436d9ca215d\"," +
                    "\"productName\": \"Масло\"," +
                    "\"price\": 11.0" +
                    "}]")))
})

public @interface GetAllProductApiResponsesOperation {

}
