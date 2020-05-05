package kg.tezal.tezal_back.api_controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import kg.tezal.tezal_back.enums.OrderStatus;
import kg.tezal.tezal_back.model.OrderModel;
import kg.tezal.tezal_back.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    private List<OrderModel> orderList;
    @Autowired
    private ObjectMapper objectMapper;

    private OrderModel order;
    @BeforeEach
    void setUp() {
        this.orderList = new ArrayList<>();
        order = new OrderModel();
        order.setId(1L);
        order.setOrganizationId(1L);
        order.setOrdersStatus(OrderStatus.AWAITING);
        OrderModel order1 = new OrderModel();
        order1.setOrganizationId(1L);
        order1.setOrdersStatus(OrderStatus.AWAITING);
        orderList.add(order);
        orderList.add(order1);
    }

    @Test
    public void getAll() throws Exception {
        final Long orgId = 1L;
        given(orderService.findAllByOrgId(1L)).willReturn(orderList);
        this.mockMvc.perform(get("/api/order/all?orgId={orgId}", orgId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(orderList.size())));
    }

    @Test
    void shouldFetchOneById() throws Exception {
        final Long id = 1L;
        given(orderService.getById(id)).willReturn((order));

        this.mockMvc.perform(get("/api/order/model/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ordersStatus", is("AWAITING")));
    }

    @Test
    void shouldReturn404WhenFindOrderById() throws Exception {
        given(orderService.findById(3L)).willReturn(null);

        ResultActions resultActions =  this.mockMvc.perform(get("/api/order/{id}", 3L))
                .andExpect(status().isOk());
        assertTrue(resultActions.andReturn().getResponse().getContentAsString().isEmpty());
    }

    @Test
    void shouldDeleteOrder() throws Exception {
        final Long id = 1L;
        given(orderService.deleteById(id)).willReturn("Record deleted");

        ResultActions resultActions = this.mockMvc.perform(delete("/api/order/{id}", order.getId()))
                .andExpect(status().isOk());

        assertEquals("Record deleted", resultActions.andReturn().getResponse().getContentAsString());
    }

}
