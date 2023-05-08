package com.example.credit.mapper;

import com.example.credit.dto.TariffDTO;
import com.example.credit.entity.tables.Tariff;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface TariffMapper {
    TariffDTO tariffToTariffDto(Tariff tariff);
    Tariff tariffDtoToTariff(TariffDTO tariffDTO);
    List<TariffDTO> tariffsToTariffsDto(List<Tariff> tariff);
    List<Tariff> tariffsDToTariffs(List<TariffDTO> tariff);
}
