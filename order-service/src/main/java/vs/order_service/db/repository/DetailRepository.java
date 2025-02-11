package vs.order_service.db.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;

@Repository
public interface DetailRepository extends JpaRepository<DetailEntity, UUID> {

  Optional<DetailEntity> findDetailEntityByOrderEntity(OrderEntity orderEntity);

}
