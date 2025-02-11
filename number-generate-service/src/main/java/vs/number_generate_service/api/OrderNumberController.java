package vs.number_generate_service.api;

import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/number-generate-service/numbers")
public interface OrderNumberController {

  @GetMapping("/generate")
  ResponseEntity<String> getOrderNumber();

  @GetMapping("/all-keys")
  ResponseEntity<Set<String>> getAllOrderNumbers();


}
