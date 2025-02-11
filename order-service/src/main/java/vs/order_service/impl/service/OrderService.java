package vs.order_service.impl.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;

public interface OrderService {

  CreateOrderResponseDto createOrder(OrderCreateRequestDto orderCreateRequestDto)
      throws UnsupportedEncodingException;

  OrderResponseDto getOrderByNumber(String orderNumber);

  List<OrderResponseDto> searchOrdersByDateAndTotalAmount(
      LocalDate date, double totalAmount);

  List<OrderResponseDto> searchOrdersByDateBetweenAndProductName(
      String productName, LocalDate startDate, LocalDate endDate);


}
