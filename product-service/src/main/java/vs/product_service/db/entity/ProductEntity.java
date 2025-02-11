package vs.product_service.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "product_code")
  private UUID productId;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(nullable = false)
  private double price;


}
