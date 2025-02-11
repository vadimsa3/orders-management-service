package vs.product_service.db.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vs.product_service.db.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

  Optional<ProductEntity> findByProductName(String productName);

  boolean existsByProductName(String productName);

}
