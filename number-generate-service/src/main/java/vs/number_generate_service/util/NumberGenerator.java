package vs.number_generate_service.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

@UtilityClass
public class NumberGenerator {

  public static String generateNumber(int length) {
    return RandomStringUtils.secure().nextNumeric(length);
  }


}
