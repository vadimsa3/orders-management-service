package vs.number_generate_service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vs.number_generate_service.impl.controller.OrderNumberControllerImpl;
import vs.number_generate_service.impl.service.NumberGeneratorService;

@ExtendWith(MockitoExtension.class)
class OrderNumberControllerTest {

  @Mock
  private NumberGeneratorService numberGeneratorService;

  private MockMvc mockMvc;

  @InjectMocks
  private OrderNumberControllerImpl orderNumberController;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(orderNumberController).build();
  }

  @Test
  void testGetOrderNumberShouldReturnOk() throws Exception {
    String expectedOrderNumber = "TEST-ORDER-NUMBER";

    when(numberGeneratorService.generateUniqOrderNumber()).thenReturn(expectedOrderNumber);

    mockMvc.perform(get("/api/number-generate-service/numbers/generate")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedOrderNumber));

    verify(numberGeneratorService, times(1)).generateUniqOrderNumber();
  }

  @Test
  void getAllOrderNumbersShouldReturnOk() throws Exception {
    Set<String> expectedSetAllNumbers = new HashSet<>();
    expectedSetAllNumbers.add("TEST-ORDER-NUMBER");
    expectedSetAllNumbers.add("TEST-ORDER-NUMBER2");

    when(numberGeneratorService.getAllOrderNumbersFromDb()).thenReturn(expectedSetAllNumbers);

    mockMvc.perform(get("/api/number-generate-service/numbers/all-keys")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("[\"TEST-ORDER-NUMBER\", \"TEST-ORDER-NUMBER2\"]"));
    verify(numberGeneratorService, times(1)).getAllOrderNumbersFromDb();
  }

}
