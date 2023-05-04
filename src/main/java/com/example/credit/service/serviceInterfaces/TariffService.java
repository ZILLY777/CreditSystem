package com.example.credit.service.serviceInterfaces;

import com.example.credit.dto.TariffDTO;

import java.util.List;

public interface TariffService {
    List<TariffDTO> getAllTariffs();
    Long save(TariffDTO tariffDTO);
}
