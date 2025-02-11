package vs.order_service.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;
import vs.order_service.db.enums.DeliveryTypeEnum;
import vs.order_service.db.enums.PaymentTypeEnum;
import vs.order_service.db.repository.DetailRepository;
import vs.order_service.db.repository.OrderRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DetailRepositoryTest {

  @Autowired
  private DetailRepository detailRepository;

  @Autowired
  private OrderRepository orderRepository;

  private OrderEntity orderEntity;

  @BeforeEach
  void setUp() {
    orderEntity = OrderEntity.builder()
        .orderNumber("ORDER-NUMBER-TEST")
        .totalAmount(100.0)
        .orderDate(LocalDate.now())
        .clientName("TEST-NAME")
        .deliveryAddress("TEST-ADDRESS")
        .paymentType(PaymentTypeEnum.CARD)
        .deliveryType(DeliveryTypeEnum.PICKUP)
        .build();

    orderRepository.save(orderEntity);
  }

  @Test
  void testFindDetailEntityByOrderEntityShouldReturnDetail() {
    DetailEntity detailEntity = DetailEntity.builder()
        .productName("TEST-PRODUCT")
        .productCode(UUID.randomUUID())
        .quantity(2)
        .price(50.0)
        .orderEntity(orderEntity)
        .build();

    detailRepository.save(detailEntity);

    Optional<DetailEntity> foundDetail = detailRepository.findDetailEntityByOrderEntity(
        orderEntity);

    assertTrue(foundDetail.isPresent());
    assertThat(foundDetail.get().getProductName()).isEqualTo("TEST-PRODUCT");
  }

  @Test
  void testFindDetailEntityByOrderEntityShouldReturnEmptyWhenNotFound() {
    OrderEntity newOrder = OrderEntity.builder()
        .orderId(UUID.randomUUID())
        .orderNumber("ANOTHER-ORDER-NUMBER")
        .totalAmount(200.0)
        .orderDate(LocalDate.now())
        .clientName("ANOTHER-NAME")
        .deliveryAddress("ANOTHER-ADDRESS")
        .paymentType(PaymentTypeEnum.CARD)
        .deliveryType(DeliveryTypeEnum.PICKUP)
        .build();

    Optional<DetailEntity> foundDetail = detailRepository.findDetailEntityByOrderEntity(newOrder);

    assertTrue(foundDetail.isEmpty());
  }


}
