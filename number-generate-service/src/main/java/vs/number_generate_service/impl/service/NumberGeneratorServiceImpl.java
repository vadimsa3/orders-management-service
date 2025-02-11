package vs.number_generate_service.impl.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import vs.number_generate_service.util.NumberGenerator;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberGeneratorServiceImpl implements NumberGeneratorService {

  @Value("${generator-params.count-start-symbols}")
  public Integer startSymbols;

  private final StringRedisTemplate stringRedisTemplate;

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public String generateUniqOrderNumber() {
    String orderNumber;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formattedDate = LocalDateTime.now().format(formatter);
    do {
      orderNumber = NumberGenerator.generateNumber(startSymbols) + formattedDate;
    } while (!addOrderNumberToRedisDb(orderNumber));
    log.info("Generated new order number: {}", orderNumber);
    return orderNumber;
  }

  public boolean addOrderNumberToRedisDb(String orderNumber) {
    Long result = stringRedisTemplate.opsForSet().add(
        "unique_order_numbers", orderNumber);
    return result != null && result > 0;
  }

  @Override
  public Set<String> getAllOrderNumbersFromDb() {
    SetOperations<String, String> setOps = redisTemplate.opsForSet();
    return setOps.members("unique_order_numbers");
  }

}
