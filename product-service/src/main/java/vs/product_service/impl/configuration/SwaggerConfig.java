package vs.product_service.impl.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Product-service",
        description = "Документация для сервиса управления продуктами",
        version = "1.0.0",
        contact = @Contact(
            name = "Vadim Savchuk",
            email = "vadimsa3@mail.ru"
        )
    )
)

@Configuration
public class SwaggerConfig {

}
