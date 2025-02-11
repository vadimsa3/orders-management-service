package vs.order_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vs.order_service.api.dto.request.OrderCreateRequestDto;
import vs.order_service.api.dto.responce.CreateOrderResponseDto;
import vs.order_service.api.dto.responce.OrderResponseDto;
import vs.order_service.impl.controller.OrderControllerImpl;
import vs.order_service.impl.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

  @Mock
  private OrderService orderService;

  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  private LocalDate testDate;

  @InjectMocks
  private OrderControllerImpl orderController;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    objectMapper.registerModule(new JavaTimeModule());
    testDate = LocalDate.of(2025, 2, 15);
  }

  @Test
  void testCreateOrderShouldReturnCreated() throws Exception {
    UUID testProductCode = UUID.randomUUID();
    OrderCreateRequestDto requestDto = new OrderCreateRequestDto(
        "Test client name", "Test address", "CARD",
        "PICKUP", "Test product", 1);
    CreateOrderResponseDto responseDto = new CreateOrderResponseDto(
        testProductCode, "Test order number", testDate,
        "Test client name", "Test address", "CARD",
        "PICKUP");

    when(orderService.createOrder(any(OrderCreateRequestDto.class))).thenReturn(responseDto);

    mockMvc.perform(post("/api/order-service/orders/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDto)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

    verify(orderService, times(1))
        .createOrder(any(OrderCreateRequestDto.class));
  }

  @Test
  void testGetOrderShouldReturnOk() throws Exception {
    String orderNumber = "TEST-ORDER-NUMBER";
    OrderResponseDto responseDto = new OrderResponseDto(UUID.randomUUID(), orderNumber,
        100.5, testDate, "Test client name", "Test address",
        "CARD", "PICKUP");

    when(orderService.getOrderByNumber(orderNumber)).thenReturn(responseDto);

    mockMvc.perform(get("/api/order-service/orders/{orderNumber}", orderNumber)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

    verify(orderService, times(1)).getOrderByNumber(orderNumber);
  }

  @Test
  void testSearchOrdersByDateAndTotalAmountShouldReturnList() throws Exception {
    UUID orderId = UUID.randomUUID();
    double totalAmount = 100.0;
    List<OrderResponseDto> responseList = Collections.singletonList(new OrderResponseDto(
        orderId, "ORDER-NUMBER", totalAmount, testDate, "TEST-NAME",
        "TEST-ADDRESS", "CARD", "PICKUP"));

    when(orderService.searchOrdersByDateAndTotalAmount(testDate, totalAmount)).thenReturn(responseList);

    mockMvc.perform(get("/api/order-service/orders/search")
            .param("date", testDate.toString())
            .param("totalAmount", String.valueOf(totalAmount))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(responseList)));

    verify(orderService, times(1))
        .searchOrdersByDateAndTotalAmount(testDate, totalAmount);
  }

  @Test
  void testSearchOrdersByDateAndExceptProductNameShouldReturnList() throws Exception {
    String productName = "TEST-PRODUCT";
    LocalDate startDate = LocalDate.of(2025, 1, 1);
    LocalDate endDate = LocalDate.of(2025, 12, 31);
    UUID orderId = UUID.randomUUID();
    double totalAmount = 100.0;

    List<OrderResponseDto> responseList = Collections.singletonList(new OrderResponseDto(
        orderId, "ORDER-NUMBER", totalAmount, testDate, "TEST-NAME",
        "TEST-ADDRESS", "CARD", "PICKUP"));

    when(orderService.searchOrdersByDateBetweenAndProductName(productName, startDate, endDate))
        .thenReturn(responseList);

    mockMvc.perform(get("/api/order-service/orders/search/except")
            .param("productName", productName)
            .param("startDate", startDate.toString())
            .param("endDate", endDate.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(responseList)));

    verify(orderService, times(1))
        .searchOrdersByDateBetweenAndProductName(productName, startDate, endDate);
  }


}