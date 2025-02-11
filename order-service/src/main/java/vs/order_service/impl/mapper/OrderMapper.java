package vs.order_service.impl.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;
import vs.order_service.db.entity.OrderEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

  OrderEntity mapToOrderEntity(OrderCreateRequestDto orderCreateRequestDto);

  CreateOrderResponseDto mapToOrderCreateResponseDto(OrderEntity orderEntity);

  OrderResponseDto mapToOrderResponseDto(OrderEntity orderEntity);

  List<OrderResponseDto> mapToOrderResponseDtoList(List<OrderEntity> orderEntities);

}
