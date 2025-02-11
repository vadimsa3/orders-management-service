package vs.order_service.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vs.order_service.api.annotation.GetDetailApiResponsesOperation;
import vs.order_service.api.dto.responce.DetailResponseDto;

@Tag(name = "Работа с деталями заказа")
@RequestMapping("api/order-service/orders/detail")
public interface DetailController {

  @Operation(summary = "Получение детальной информации о заказе.",
      description = "Получение детальной информации о заказе. ")
  @GetDetailApiResponsesOperation
  @GetMapping("/{orderId}")
  ResponseEntity<DetailResponseDto> getDetail(@PathVariable UUID orderId);

}
