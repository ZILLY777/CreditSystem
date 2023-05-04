package com.example.credit.service;

import com.example.credit.entity.tables.Tariff;
import com.example.credit.mapper.TariffMapper;
import com.example.credit.service.serviceImpl.TariffServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RequiredArgsConstructor
public class TariffServiceTest {

    @Autowired
    private TariffServiceImpl tariffService;

    @Autowired
    private TariffMapper tariffMapper;

    @Test
    void testGeneral() throws InterruptedException {
        assertEquals(tariffMapper.tariffsToTariffsDto(List.of(new Tariff(1, "CONSUMER", "6%"),
                new Tariff(2, "MORTGAGE", "11%"),
                new Tariff(3, "TRADE", "13%"),
                new Tariff(4, "REVOLVING", "14%"),
                new Tariff(5, "INSTALLMENT", "3%"))
        ), tariffService.getAllTariffs());
    }
}
