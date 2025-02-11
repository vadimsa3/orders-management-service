package vs.number_generate_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import vs.number_generate_service.impl.service.NumberGeneratorServiceImpl;

@ExtendWith(MockitoExtension.class)
class NumberGeneratorServiceImplTest {

  @Mock
  private StringRedisTemplate stringRedisTemplate;

  @Mock
  private SetOperations<String, String> setOperations;

  @InjectMocks
  private NumberGeneratorServiceImpl numberGeneratorService;

  @BeforeEach
  void setUp() {
    numberGeneratorService.startSymbols = 5;
  }

  @Test
  void testGenerateUniqOrderNumberShouldGenerateUniqueOrderNumber() {
    String expectedPrefix = "00001";
    String expectedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    String expectedOrderNumber = expectedPrefix + expectedDate;

    when(stringRedisTemplate.opsForSet()).thenReturn(setOperations);
    when(setOperations.add(anyString(), anyString())).thenReturn(1L);

    String generatedOrderNumber = numberGeneratorService.generateUniqOrderNumber();

    assertEquals(expectedOrderNumber.substring(0, 5).length(),
        generatedOrderNumber.substring(0, 5).length());
    assertEquals(expectedOrderNumber.substring(5, 10),
        generatedOrderNumber.substring(5, 10));

    verify(setOperations).add("unique_order_numbers", generatedOrderNumber);
  }

  @Test
  void testAddOrderNumberToRedisDbShouldReturnTrueWhenAddedSuccess() {
    when(stringRedisTemplate.opsForSet()).thenReturn(setOperations);
    when(stringRedisTemplate.opsForSet()
        .add("unique_order_numbers", "TEST-ORDER-NUMBER")).thenReturn(1L);
    boolean result = numberGeneratorService.addOrderNumberToRedisDb("TEST-ORDER-NUMBER");
    assertTrue(result);
  }

  @Test
  void testAddOrderNumberToRedisDbShouldReturnFalseWhenNotAdded() {
    when(stringRedisTemplate.opsForSet()).thenReturn(setOperations);
    when(stringRedisTemplate.opsForSet()
        .add("unique_order_numbers", "TEST-ORDER-NUMBER")).thenReturn(0L);
    boolean result = numberGeneratorService.addOrderNumberToRedisDb("TEST-ORDER-NUMBER");
    assertFalse(result);
  }

  @Test
  void testAddOrderNumberToRedisDbShouldReturnFalseWhenResultIsNull() {
    when(stringRedisTemplate.opsForSet()).thenReturn(setOperations);
    when(stringRedisTemplate.opsForSet()
        .add("unique_order_numbers", "TEST-ORDER-NUMBER")).thenReturn(null);
    boolean result = numberGeneratorService.addOrderNumberToRedisDb("TEST-ORDER-NUMBER");
    assertFalse(result);
  }

  @Test
  void getAllOrderNumbersShouldReturnAllKeysFromDb() {
    Set<String> expectedKeys = new HashSet<>();
    expectedKeys.add("TEST-KEY1");
    expectedKeys.add("TEST-KEY2");

    when(stringRedisTemplate.keys("*")).thenReturn(expectedKeys);

    Set<String> actualKeys = numberGeneratorService.getAllOrderNumbersFromDb();

    verify(stringRedisTemplate, times(1)).keys("*");
    assertEquals(expectedKeys, actualKeys);
  }


}








