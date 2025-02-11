package vs.order_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import vs.order_service.api.dto.responce.DetailResponseDto;
import vs.order_service.impl.controller.DetailControllerImpl;
import vs.order_service.impl.service.DetailService;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DetailControllerTest {

    @Mock
    private DetailService detailService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @InjectMocks
    private DetailControllerImpl detailController;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(detailController).build();
    }

    @Test
    void testGetDetailShouldReturnOk() throws Exception {
        UUID orderId = UUID.randomUUID();
        DetailResponseDto detailResponseDto = new DetailResponseDto(
                orderId, "Test product", UUID.randomUUID(),
                5, 10);

        when(detailService.getDetail(orderId)).thenReturn(detailResponseDto);

        mockMvc.perform(get("/api/order-service/orders/detail/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(detailResponseDto)));
        verify(detailService, times(1)).getDetail(orderId);
    }


}
