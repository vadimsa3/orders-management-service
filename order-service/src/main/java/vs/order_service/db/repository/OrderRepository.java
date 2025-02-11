package vs.order_service.db.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vs.order_service.db.entity.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

  Optional<OrderEntity> findByOrderNumber(String orderNumber);


  @Query("SELECT o FROM OrderEntity o " +
      "WHERE o.totalAmount > :totalAmount " +
      "AND o.orderDate = :orderDate")
  List<OrderEntity> findByOrderDateBetween(
      @Param("orderDate") LocalDate orderDate,
      @Param("totalAmount") double totalAmount);

  @Query("SELECT o FROM OrderEntity o " +
      "JOIN o.orderDetails d " +
      "WHERE d.productName != :productName " +
      "AND o.orderDate BETWEEN :startDate AND :endDate")
  List<OrderEntity> findOrdersExcludingProductAndWithinDateRange(
      @Param("productName") String productName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

}
