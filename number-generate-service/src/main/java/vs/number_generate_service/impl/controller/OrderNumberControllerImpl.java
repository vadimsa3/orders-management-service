package vs.number_generate_service.impl.controller;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vs.number_generate_service.impl.service.NumberGeneratorService;
import vs.number_generate_service.api.OrderNumberController;

@RestController
@RequiredArgsConstructor
public class OrderNumberControllerImpl implements OrderNumberController {

  private final NumberGeneratorService numberGeneratorService;

  @Override
  public ResponseEntity<String> getOrderNumber() {
    return ResponseEntity.ok(numberGeneratorService.generateUniqOrderNumber());
  }

  @Override
  public ResponseEntity<Set<String>> getAllOrderNumbers() {
    return ResponseEntity.ok(numberGeneratorService.getAllOrderNumbersFromDb());
  }


}
