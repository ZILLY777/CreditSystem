package com.example.credit.service.serviceInterfaces;

import com.example.credit.dto.TariffDTO;

import java.util.List;

public interface TariffService {
    List<TariffDTO> getAllTariffs() throws InterruptedException;
    void save(TariffDTO tariffDTO);
}
