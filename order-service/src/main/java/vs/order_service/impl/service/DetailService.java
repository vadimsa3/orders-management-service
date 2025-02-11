package vs.order_service.impl.service;

import java.util.UUID;
import vs.order_service.api.dto.responce.DetailResponseDto;

public interface DetailService {

  DetailResponseDto getDetail(UUID orderId);


}
