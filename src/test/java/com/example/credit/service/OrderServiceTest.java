package com.example.credit.service;


import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.exceptions.LoanConsiderationException;
import com.example.credit.exceptions.TariffNotFoundException;
import com.example.credit.mapper.TariffMapper;
import com.example.credit.service.serviceImpl.OrderServiceImpl;
import com.example.credit.service.serviceImpl.TariffServiceImpl;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@RequiredArgsConstructor
@Transactional
public class OrderServiceTest {
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void testPostEmptyLoanOrder() {
        LoanOrderDTO dto = new LoanOrderDTO();
        Exception exception = assertThrows(TariffNotFoundException.class, () -> {
            orderService.postOrder(dto);
        });

        String expectedMessage = "TARIFF_NOT_FOUND";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testNormalLoanOrder() {
        LoanOrderDTO dto = new LoanOrderDTO();
        dto.setTariffId(1);
        dto.setUserId(10000);
        assertEquals(orderService.postOrder(dto).getClass(), UUID.class);
    }

    @Test
    void testInProgressLoanOrder() {
        LoanOrderDTO dto = new LoanOrderDTO();
        dto.setTariffId(1);
        dto.setUserId(10000);
        Exception exception = assertThrows(LoanConsiderationException.class, () -> {
            orderService.postOrder(dto);
            orderService.postOrder(dto);
        });

        String expectedMessage = "LOAN_CONSIDERATION";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }







}

