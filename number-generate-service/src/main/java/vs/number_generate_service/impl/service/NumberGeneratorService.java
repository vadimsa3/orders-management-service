package vs.number_generate_service.impl.service;

import java.util.Set;

public interface NumberGeneratorService {

  String generateUniqOrderNumber();

  Set<String> getAllOrderNumbersFromDb();


}
