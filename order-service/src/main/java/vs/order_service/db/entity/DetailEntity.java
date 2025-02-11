package vs.order_service.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "details")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DetailEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "detail_id")
  private UUID detailId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "product_code", nullable = false)
  private UUID productCode;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private double price;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity orderEntity;


}
