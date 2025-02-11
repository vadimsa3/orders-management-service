package vs.order_service.impl.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vs.exception_handler_starter.exception.AppException;
import vs.exception_handler_starter.exception.EnumException;
import vs.order_service.api.dto.responce.DetailResponseDto;
import vs.order_service.db.entity.DetailEntity;
import vs.order_service.db.entity.OrderEntity;
import vs.order_service.db.repository.DetailRepository;
import vs.order_service.db.repository.OrderRepository;
import vs.order_service.impl.mapper.DetailMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

  private final DetailRepository detailRepository;

  private final OrderRepository orderRepository;

  private final DetailMapper detailMapper;

  @Override
  public DetailResponseDto getDetail(UUID orderId) {
    OrderEntity orderEntity = orderRepository.findById(orderId)
        .orElseThrow(() ->
            new AppException(EnumException.NOT_FOUND,
                String.format("Order with Id %s not found", orderId)));
    DetailEntity detailEntity = detailRepository.findDetailEntityByOrderEntity(orderEntity)
        .orElseThrow(() -> new AppException(
            EnumException.NOT_FOUND,
            String.format("Detail with orderId %s not found ", orderId)));
    log.info("Detail with orderId {} founded", detailEntity.getOrderEntity().getOrderId());
    return detailMapper.mapToDetailResponseDto(detailEntity);
  }


}

