package vs.order_service.impl.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vs.order_service.api.controller.DetailController;
import vs.order_service.api.dto.responce.DetailResponseDto;
import vs.order_service.impl.service.DetailService;

@RestController
@AllArgsConstructor
public class DetailControllerImpl implements DetailController {

  private DetailService detailService;

  @Override
  public ResponseEntity<DetailResponseDto> getDetail(
      @Parameter(description = "Id заказа", example = "b28927dd-4332-4828-9d89-0e765205eab0")
      UUID orderId) {
    DetailResponseDto detailResponseDto = detailService.getDetail(orderId);
    return new ResponseEntity<>(detailResponseDto, HttpStatus.OK);
  }


}
