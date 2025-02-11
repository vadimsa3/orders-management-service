package vs.order_service.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;
import vs.order_service.api.dto.responce.ProductResponseDto;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;
import vs.order_service.db.enums.DeliveryTypeEnum;
import vs.order_service.db.enums.PaymentTypeEnum;

public class Fixtures {

  public static String orderNumber = "ORDER-NUMBER-TEST";

  public static String productName = "TEST-PRODUCT";

  public static String clientName = "TEST-NAME";

  public static String deliveryAddress = "TEST-ADDRESS";

  public static String paymentType = "CARD";

  public static String deliveryType = "PICKUP";

  public static LocalDate orderDate = LocalDate.of(2025, 1, 10);

  public static double price = 50.0;

  public static int quantity = 2;

  public static double totalAmount = 100.0;

  public static UUID productId = UUID.fromString("7b5c7537-51b4-4fd8-8e1b-171931c6125b");

  public static UUID orderId = UUID.fromString("1690d7dd-6d80-47cb-bde5-f5e11e65e95e");

  public static OrderEntity getOrderEntity() {
    return OrderEntity.builder()
        .orderNumber(orderNumber)
        .totalAmount(totalAmount)
        .orderDate(orderDate)
        .clientName(clientName)
        .deliveryAddress(deliveryAddress)
        .paymentType(PaymentTypeEnum.valueOf(paymentType))
        .deliveryType(DeliveryTypeEnum.valueOf(deliveryType))
        .orderDetails(new ArrayList<>())
        .build();
  }

  public static DetailEntity getDetailEntity(OrderEntity orderEntity) {
    return DetailEntity.builder()
        .productName(productName)
        .productCode(productId)
        .quantity(quantity)
        .price(price)
        .orderEntity(orderEntity)
        .build();
  }

  public static List<DetailEntity> getDetailEntities(OrderEntity order) {
    List<DetailEntity> details = new ArrayList<>();
    details.add(getDetailEntity(order));
    details.add(getDetailEntity(order));
    return details;
  }

  public static OrderCreateRequestDto getOrderCreateRequestDto() {
    return new OrderCreateRequestDto(clientName, deliveryAddress, paymentType, deliveryType,
        productName, quantity);
  }

  public static CreateOrderResponseDto getCreateOrderResponseDto() {
    return new CreateOrderResponseDto(
        orderId, orderNumber, orderDate, clientName, deliveryAddress, paymentType, deliveryType);
  }

  public static ProductResponseDto getProductResponseDto() {
    return ProductResponseDto.builder()
        .productId(productId).productName(productName).price(price).build();
  }

  public static OrderResponseDto getOrderResponseDto() {
    return OrderResponseDto.builder()
        .orderId(orderId).orderNumber(orderNumber).totalAmount(totalAmount)
        .orderDate(orderDate).clientName(clientName).deliveryAddress(deliveryAddress)
        .paymentType(paymentType).deliveryType(deliveryType).build();
  }

  public static List<OrderResponseDto> getListOrderResponseDto() {
    return Collections.singletonList(getOrderResponseDto());
  }

}
