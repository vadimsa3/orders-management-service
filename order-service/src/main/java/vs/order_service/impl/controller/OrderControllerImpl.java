package vs.order_service.impl.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vs.order_service.api.controller.OrderController;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;
import vs.order_service.impl.service.OrderService;

@RestController
@AllArgsConstructor
public class OrderControllerImpl implements OrderController {

  private final OrderService orderService;

  @Override
  public ResponseEntity<CreateOrderResponseDto> createOrder(
      OrderCreateRequestDto orderCreateRequestDto) throws UnsupportedEncodingException {
    CreateOrderResponseDto orderCreateResponseDto = orderService.createOrder(orderCreateRequestDto);
    return new ResponseEntity<>(orderCreateResponseDto, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<OrderResponseDto> getOrder(
      @Parameter(description = "Номер заказа ", example = "5711420250127") String orderNumber) {
    OrderResponseDto orderResponseDto = orderService.getOrderByNumber(orderNumber);
    return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<OrderResponseDto>> searchOrdersByDateAndTotalAmount(
      @Parameter(description = "Дата ", example = "2025-02-31") LocalDate date,
      @Parameter(description = "Сумма заказа ", example = "10.5") double totalAmount) {
    List<OrderResponseDto> listOrdersResponseDto =
        orderService.searchOrdersByDateAndTotalAmount(date, totalAmount);
    return new ResponseEntity<>(listOrdersResponseDto, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<OrderResponseDto>> searchOrdersByDateAndExceptProductName(
      @Parameter(description = "Исключаемый продукт ", example = "Молоко") String productName,
      @Parameter(description = "Начало диапазона ", example = "2025-02-25") LocalDate startDate,
      @Parameter(description = "Конец диапазона ", example = "2025-02-31") LocalDate endDate) {
    List<OrderResponseDto> listOrdersResponseDto =
        orderService.searchOrdersByDateBetweenAndProductName(productName, startDate, endDate);
    return new ResponseEntity<>(listOrdersResponseDto, HttpStatus.OK);
  }


}
