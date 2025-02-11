package vs.order_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import vs.exception_handler_starter.exception.AppException;
import vs.exception_handler_starter.exception.EnumException;
import vs.order_service.api.dto.responce.DetailResponseDto;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;
import vs.order_service.db.repository.DetailRepository;
import vs.order_service.db.repository.OrderRepository;
import vs.order_service.impl.mapper.DetailMapper;
import vs.order_service.impl.service.DetailServiceImpl;

@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "vs.exception_handler_starter.handler")
class DetailServiceImplTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private DetailRepository detailRepository;

  @Mock
  private DetailMapper detailMapper;

  @InjectMocks
  private DetailServiceImpl detailService;

  private UUID orderId;

  @BeforeEach
  void setUp() {
    orderId = UUID.randomUUID();
  }

  @Test
  void testGetDetailShouldReturnDetailResponseDto() {
    UUID detailId = UUID.randomUUID();
    UUID productCode = UUID.randomUUID();

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setOrderId(orderId);

    DetailEntity detailEntity = new DetailEntity();
    detailEntity.setOrderEntity(orderEntity);

    DetailResponseDto responseDto = new DetailResponseDto(
        detailId, "TEST", productCode, 1, 10.0);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
    when(detailRepository.findDetailEntityByOrderEntity(orderEntity)).thenReturn(
        Optional.of(detailEntity));
    when(detailMapper.mapToDetailResponseDto(detailEntity)).thenReturn(responseDto);

    DetailResponseDto result = detailService.getDetail(orderId);

    assertNotNull(result);
    assertEquals(responseDto, result);

    verify(orderRepository, times(1))
        .findById(orderId);
    verify(detailRepository, times(1))
        .findDetailEntityByOrderEntity(orderEntity);
    verify(detailMapper, times(1))
        .mapToDetailResponseDto(detailEntity);
  }

  @Test
  void testGetDetailShouldThrowNotFoundExceptionForOrder() {
    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    AppException exception = assertThrows(AppException.class,
        () -> detailService.getDetail(orderId));

    assertEquals(EnumException.NOT_FOUND, exception.getEnumInterface());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    assertEquals(String.format("Order with Id %s not found", orderId), exception.getErrorMessage());

    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void testGetDetailShouldThrowNotFoundExceptionForDetail() {
    OrderEntity orderEntity = new OrderEntity();

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
    when(detailRepository.findDetailEntityByOrderEntity(orderEntity)).thenReturn(Optional.empty());

    AppException exception = assertThrows(AppException.class,
        () -> detailService.getDetail(orderId));

    assertEquals(EnumException.NOT_FOUND, exception.getEnumInterface());
    assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    assertEquals(String.format("Detail with orderId %s not found ", orderId),
        exception.getErrorMessage());

    verify(orderRepository, times(1))
        .findById(orderId);
    verify(detailRepository, times(1))
        .findDetailEntityByOrderEntity(orderEntity);
  }


}
