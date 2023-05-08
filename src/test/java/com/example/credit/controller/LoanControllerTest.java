package com.example.credit.controller;


import com.example.credit.constants.OrderStatusEnum;
import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.dto.TariffDTO;
import com.example.credit.repository.repositoryInterfaces.LoanOrderRepository;
import com.example.credit.repository.repositoryInterfaces.TariffRepository;
import com.example.credit.service.serviceImpl.OrderServiceImpl;
import com.example.credit.service.serviceImpl.TariffServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class LoanControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TariffServiceImpl tariffService;

    @Autowired
    private LoanOrderRepository orderRepository;

    @Autowired
    private ObjectMapper mapper;

    private static String uuid;
    private static long userId;


    @Test
    @WithMockUser(username = "user", password = "password")
    public void getTariffs() throws Exception {
        String str = mapper.writeValueAsString(tariffService.getAllTariffs());
        MvcResult result =mockMvc.perform(get("/loan-service/getTariffs")).andReturn();
        Assertions.assertEquals(JsonPath.parse(result.getResponse().getContentAsString()).read("$.data.tariffs").toString(), str);
        }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void postOrder() throws Exception {
        userId =(long)( 1 + Math.random()*15000);
        LoanOrderDTO dto = new LoanOrderDTO();
        dto.setUserId(userId);
        dto.setTariffId(2);
        MvcResult mvcResult = this.mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andReturn();

        uuid = JsonPath.parse(mvcResult.getResponse().getContentAsString()).read("$.data.orderId");
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void getStatusOrder() throws Exception {
        postOrder();
        this.mockMvc.perform(get("/loan-service/getStatusOrder").param("orderId", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data.orderStatus").value(OrderStatusEnum.IN_PROGRESS.toString()))
                .andReturn();;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void deleteOrder() throws Exception {
        postOrder();
        LoanOrderDTO dto = new LoanOrderDTO();
        dto.setUserId(userId);
        dto.setOrderId(UUID.fromString(uuid));
        this.mockMvc.perform(delete("/loan-service/deleteOrder")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain;charset=UTF-8"))
                .andReturn();;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void postOrderWithIncorrectTariff() throws Exception {
        userId =(long)( 1 + Math.random()*15000);
        LoanOrderDTO dto = new LoanOrderDTO();
        dto.setUserId(userId);
        dto.setTariffId(0);
        MvcResult mvcResult = this.mockMvc.perform(post("/loan-service/order")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error.code").value("TARIFF_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("Тариф не найден"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void getNonExistenceOrder() throws Exception {
        uuid = String.valueOf(UUID.randomUUID());
        this.mockMvc.perform(get("/loan-service/getStatusOrder").param("orderId", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.error.code").value("ORDER_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("Заявка не найдена"))
                .andReturn();;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void deleteNonExistenceOrder() throws Exception {
        LoanOrderDTO dto = new LoanOrderDTO();
        UUID local = UUID.randomUUID();
        dto.setUserId(1000000001);
        dto.setOrderId(local);
        this.mockMvc.perform(delete("/loan-service/deleteOrder")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.error.code").value("ORDER_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("Заявка не найдена"))
                .andReturn();;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void deleteImpossibleToDeleteOrder() throws Exception {
        postOrder();
        LoanOrderDTO dto = new LoanOrderDTO();
        dto.setUserId(userId);
        dto.setOrderId(UUID.fromString(uuid));
        orderRepository.updateOrderStatusByOrderId(OrderStatusEnum.REFUSED.toString(), new Timestamp(System.currentTimeMillis()),
                uuid);
        this.mockMvc.perform(delete("/loan-service/deleteOrder")
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.error.code").value("ORDER_IMPOSSIBLE_TO_DELETE"))
                .andExpect(jsonPath("$.error.message").value("Невозможно удалить заявку"))
                .andReturn();

    }


}
