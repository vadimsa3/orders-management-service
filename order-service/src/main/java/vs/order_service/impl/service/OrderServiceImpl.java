package vs.order_service.impl.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import vs.exception_handler_starter.exception.AppException;
import vs.exception_handler_starter.exception.EnumException;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;
import vs.order_service.api.dto.responce.ProductResponseDto;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;
import vs.order_service.db.repository.DetailRepository;
import vs.order_service.db.repository.OrderRepository;
import vs.order_service.impl.mapper.OrderMapper;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final DetailRepository detailRepository;

  private final OrderMapper orderMapper;

  private final RestTemplate restTemplate;

  private final EmailService emailService;

  @Value("${number-generator-service-params.url-generator-service}")
  public String urlGeneratorService;

  @Value("${product-service-params.url-product-service-for-product}")
  public String urlProductService;

  @Override
  public CreateOrderResponseDto createOrder(OrderCreateRequestDto orderCreateRequestDto) {
    String orderNumber = restTemplate.getForObject(urlGeneratorService, String.class);
    String productName = orderCreateRequestDto.productName();

    ProductResponseDto productResponseDto = getProductFromProductService(productName);
    UUID productCode = productResponseDto.productId();
    double price = productResponseDto.price();
    double totalAmount = price * orderCreateRequestDto.quantity();

    OrderEntity orderEntity = orderMapper.mapToOrderEntity(orderCreateRequestDto);
    orderEntity.setOrderNumber(orderNumber);
    orderEntity.setTotalAmount(totalAmount);
    orderEntity.setOrderDate(LocalDate.now());

    DetailEntity detailEntity = new DetailEntity();
    detailEntity.setProductName(productName);
    detailEntity.setProductCode(productCode);
    detailEntity.setQuantity(orderCreateRequestDto.quantity());
    detailEntity.setPrice(price);
    detailEntity.setOrderEntity(orderEntity);

    orderRepository.save(orderEntity);
    detailRepository.save(detailEntity);

    log.info("Order with number {} created", orderEntity.getOrderNumber());

//    emailService.sendSimpleMessage("user_email@mail.ru", "Создание нового заказа",
//        String.format("Заказ с номером %s успешно создан.", orderEntity.getOrderNumber()));

    return orderMapper.mapToOrderCreateResponseDto(orderEntity);
  }

  @Override
  public OrderResponseDto getOrderByNumber(String orderNumber) {
    OrderEntity orderEntity = orderRepository.findByOrderNumber(orderNumber)
        .orElseThrow(() ->
            new AppException(EnumException.NOT_FOUND,
                String.format("Order with number %s not found", orderNumber)));
    log.info("Order with number {} founded", orderEntity.getOrderNumber());
    return orderMapper.mapToOrderResponseDto(orderEntity);
  }

  @Override
  public List<OrderResponseDto> searchOrdersByDateAndTotalAmount(LocalDate date,
      double totalAmount) {
    List<OrderEntity> ordersByDateAndTotalAmount =
        orderRepository.findByOrderDateBetween(date, totalAmount);
    if (ordersByDateAndTotalAmount.isEmpty()) {
      throw new AppException(EnumException.NOT_FOUND, HttpStatus.NOT_FOUND,
          "No orders by date and total amount founded");
    }
    log.info("Orders for the specified date and cost above {} founded", totalAmount);
    return orderMapper.mapToOrderResponseDtoList(ordersByDateAndTotalAmount);
  }

  @Override
  public List<OrderResponseDto> searchOrdersByDateBetweenAndProductName(String productName,
      LocalDate startDate, LocalDate endDate) {
    List<OrderEntity> ordersBetweenDateAndExceptProductName =
        orderRepository.findOrdersExcludingProductAndWithinDateRange(productName, startDate,
            endDate);
    if (ordersBetweenDateAndExceptProductName.isEmpty()) {
      throw new AppException(EnumException.NOT_FOUND, HttpStatus.NOT_FOUND,
          "No orders by date and except product name founded");
    }
    log.info("Orders for the specified date and except product name {} founded", productName);
    return orderMapper.mapToOrderResponseDtoList(ordersBetweenDateAndExceptProductName);
  }

  private ProductResponseDto getProductFromProductService(String productName) {
    String urlRequestToProductService = urlProductService + productName;
    return restTemplate.getForObject(urlRequestToProductService, ProductResponseDto.class);
  }


}
