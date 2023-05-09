package com.example.credit.service.serviceImpl;

import com.example.credit.dto.TariffDTO;
import com.example.credit.mapper.TariffMapper;
import com.example.credit.repository.repositoryInterfaces.TariffRepository;
import com.example.credit.service.serviceInterfaces.TariffService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;

    @CircuitBreaker(name = "basicBreaker")
    public List<TariffDTO> getAllTariffs() {
        return tariffMapper.tariffsToTariffsDto(tariffRepository.findAll());
    }
    @Override
    public void save(TariffDTO tariffDTO) {
       tariffRepository.save(tariffMapper.tariffDtoToTariff(tariffDTO));
    }
}
