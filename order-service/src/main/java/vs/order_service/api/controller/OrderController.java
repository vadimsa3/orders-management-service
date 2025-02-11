package vs.order_service.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vs.order_service.api.annotation.CreateOrderApiResponsesOperation;
import vs.order_service.api.annotation.GetOrderApiResponsesOperation;
import vs.order_service.api.annotation.SearchOrdersByDateAndExceptProductNameApiResponsesOperation;
import vs.order_service.api.annotation.SearchOrdersByDateAndTotalAmountApiResponsesOperation;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;

@Tag(name = "Управление заказами")
@RequestMapping("api/order-service/orders")
public interface OrderController {

  @Operation(summary = "Создание нового заказа.",
      description = "Формирование нового заказа с учетом взаимодействия "
          + "с сервисом генерации номера заказа и сервисом управления продуктами. ")
  @CreateOrderApiResponsesOperation
  @PostMapping("/create")
  ResponseEntity<CreateOrderResponseDto> createOrder(
      @RequestBody OrderCreateRequestDto orderCreateRequestDto) throws UnsupportedEncodingException;

  @Operation(summary = "Получение информации о заказе.",
      description = "Получение основной информации о сформированном заказе. ")
  @GetOrderApiResponsesOperation
  @GetMapping("/{orderNumber}")
  ResponseEntity<OrderResponseDto> getOrder(
      @PathVariable("orderNumber") String orderNumber);

  @Operation(summary = "Поиск заказов по заданным параметрам.",
      description = "Получение информации о заказе (заказам) на заданную дату "
          + "и больше заданной общей суммы заказа. ")
  @SearchOrdersByDateAndTotalAmountApiResponsesOperation
  @GetMapping("/search")
  ResponseEntity<List<OrderResponseDto>> searchOrdersByDateAndTotalAmount(
      @RequestParam("date") LocalDate date,
      @RequestParam("totalAmount") double totalAmount);

  @Operation(summary = "Поиск заказов по заданным параметрам.",
      description = "Получение списка заказов, не содержащих заданный товар "
          + "и поступивших в заданный временной период. ")
  @SearchOrdersByDateAndExceptProductNameApiResponsesOperation
  @GetMapping("/search/except")
  ResponseEntity<List<OrderResponseDto>> searchOrdersByDateAndExceptProductName(
      @RequestParam("productName") String productName,
      @RequestParam("startDate") LocalDate startDate,
      @RequestParam("endDate") LocalDate endDate);


}
