package vs.order_service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import vs.order_service.api.dto.responce.DetailResponseDto;
import vs.order_service.db.entity.DetailEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DetailMapper {

  DetailResponseDto mapToDetailResponseDto(DetailEntity detailEntity);

}
