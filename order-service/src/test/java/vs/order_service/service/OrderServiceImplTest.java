package vs.order_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vs.order_service.util.Fixtures.getCreateOrderResponseDto;
import static vs.order_service.util.Fixtures.getDetailEntity;
import static vs.order_service.util.Fixtures.getListOrderResponseDto;
import static vs.order_service.util.Fixtures.getOrderCreateRequestDto;
import static vs.order_service.util.Fixtures.getOrderEntity;
import static vs.order_service.util.Fixtures.getOrderResponseDto;
import static vs.order_service.util.Fixtures.getProductResponseDto;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import vs.exception_handler_starter.exception.AppException;
import vs.exception_handler_starter.exception.EnumException;
import vs.order_service.util.Fixtures;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;
import vs.order_service.api.dto.responce.ProductResponseDto;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;
import vs.order_service.db.repository.DetailRepository;
import vs.order_service.db.repository.OrderRepository;
import vs.order_service.impl.mapper.OrderMapper;
import vs.order_service.impl.service.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private DetailRepository detailRepository;

  @Mock
  private OrderMapper orderMapper;

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private OrderServiceImpl orderService;

  private final String urlGeneratorService = "http://localhost/generate";

  private final String urlProductService = "http://localhost/product/";

  private String orderNumber;

  private LocalDate date;

  private double totalAmount;

  private String productName;

  @BeforeEach
  void setUp() {
    orderService.urlGeneratorService = urlGeneratorService;
    orderService.urlProductService = urlProductService;
    orderNumber = Fixtures.orderNumber;
    date = LocalDate.now();
    totalAmount = Fixtures.totalAmount;
    productName = Fixtures.productName;
  }

  @Test
  void testCreateOrderShouldReturnCreateOrderResponseDto() {
    OrderCreateRequestDto orderCreateRequestDto = getOrderCreateRequestDto();
    CreateOrderResponseDto createOrderResponseDto = getCreateOrderResponseDto();
    ProductResponseDto productResponseDto = getProductResponseDto();

    when(restTemplate.getForObject(urlGeneratorService, String.class)).thenReturn(
        orderNumber);
    when(restTemplate.getForObject(urlProductService + orderCreateRequestDto.productName(),
        ProductResponseDto.class)).thenReturn(productResponseDto);

    OrderEntity orderEntity = getOrderEntity();
    DetailEntity detailEntity = getDetailEntity(orderEntity);

    when(orderMapper.mapToOrderEntity(orderCreateRequestDto)).thenReturn(orderEntity);
    when(orderMapper.mapToOrderCreateResponseDto(orderEntity))
        .thenReturn(createOrderResponseDto);

    CreateOrderResponseDto response = orderService.createOrder(orderCreateRequestDto);

    assertNotNull(response);
    assertEquals(orderNumber, response.orderNumber());

    verify(orderRepository, times(1)).save(orderEntity);
    verify(detailRepository, times(1)).save(detailEntity);
  }

  @Test
  void testGetOrderByNumberShouldReturnOrderResponseDto() {
    OrderEntity orderEntity = getOrderEntity();

    when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(Optional.of(orderEntity));

    OrderResponseDto responseDto = getOrderResponseDto();

    when(orderMapper.mapToOrderResponseDto(orderEntity)).thenReturn(responseDto);

    OrderResponseDto result = orderService.getOrderByNumber(orderNumber);

    assertNotNull(result);

    verify(orderRepository, times(1)).findByOrderNumber(orderNumber);
  }

  @Test
  void testGetOrderByNumberShouldThrowExceptionWhenNotFound() {

    when(orderRepository.findByOrderNumber(orderNumber)).thenReturn(Optional.empty());

    AppException exception = assertThrows(AppException.class,
        () -> orderService.getOrderByNumber(orderNumber));

    assertEquals(EnumException.NOT_FOUND, exception.getEnumInterface());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    assertEquals(String.format("Order with number %s not found", orderNumber),
        exception.getErrorMessage());

    verify(orderRepository, times(1)).findByOrderNumber(orderNumber);
  }

  @Test
  void testSearchOrdersByDateAndTotalAmountShouldReturnList() {

    List<OrderEntity> ordersList = Collections.singletonList(new OrderEntity());

    when(orderRepository.findByOrderDateBetween(date, totalAmount)).thenReturn(ordersList);

    List<OrderResponseDto> listResponseDto = getListOrderResponseDto();

    when(orderMapper.mapToOrderResponseDtoList(ordersList)).thenReturn(listResponseDto);

    List<OrderResponseDto> result = orderService
        .searchOrdersByDateAndTotalAmount(date, totalAmount);

    assertNotNull(result);
    assertEquals(1, result.size());

    verify(orderRepository, times(1))
        .findByOrderDateBetween(date, totalAmount);
  }

  @Test
  void testSearchOrdersByDateAndTotalAmountShouldThrowExceptionWhenNoOrdersFound() {

    when(orderRepository.findByOrderDateBetween(date, totalAmount)).thenReturn(
        Collections.emptyList());

    AppException exception = assertThrows(AppException.class, () ->
        orderService.searchOrdersByDateAndTotalAmount(date, totalAmount));

    assertEquals(EnumException.NOT_FOUND, exception.getEnumInterface());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    assertEquals("No orders by date and total amount founded",
        exception.getErrorMessage());

    verify(orderRepository, times(1))
        .findByOrderDateBetween(date, totalAmount);
  }

  @Test
  void testSearchOrdersByDateBetweenAndProductNameShouldReturnList() {
    LocalDate startDate = LocalDate.now().minusDays(10);
    LocalDate endDate = LocalDate.now();

    List<OrderEntity> ordersList = Collections.singletonList(new OrderEntity());

    when(orderRepository.findOrdersExcludingProductAndWithinDateRange(productName, startDate,
        endDate)).thenReturn(ordersList);

    List<OrderResponseDto> listResponseDto = getListOrderResponseDto();

    when(orderMapper.mapToOrderResponseDtoList(ordersList)).thenReturn(listResponseDto);

    List<OrderResponseDto> result = orderService.searchOrdersByDateBetweenAndProductName(
        productName, startDate, endDate);

    assertNotNull(result);
    assertEquals(1, result.size());

    verify(orderRepository, times(1)).findOrdersExcludingProductAndWithinDateRange(productName,
        startDate, endDate);
  }

  @Test
  void testSearchOrdersByDateBetweenAndProductNameShouldThrowExceptionWhenNoOrdersFound() {
    LocalDate startDate = LocalDate.now().minusDays(10);
    LocalDate endDate = LocalDate.now();

    when(orderRepository.findOrdersExcludingProductAndWithinDateRange(productName, startDate,
        endDate))
        .thenReturn(Collections.emptyList());

    AppException exception = assertThrows(AppException.class, () ->
        orderService.searchOrdersByDateBetweenAndProductName(productName, startDate, endDate));

    assertEquals(EnumException.NOT_FOUND, exception.getEnumInterface());

    verify(orderRepository, times(1))
        .findOrdersExcludingProductAndWithinDateRange(productName, startDate, endDate);
  }


}
