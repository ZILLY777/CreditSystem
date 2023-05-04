package com.example.credit.service.serviceImpl;

import com.example.credit.dto.TariffDTO;
import com.example.credit.mapper.TariffMapper;
import com.example.credit.repository.repositoryInterfaces.TariffRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl {

    private final TariffRepository tariffRepository;
    private final TariffMapper tariffMapper;

    public List<TariffDTO> getAllTariffs(){
        return tariffMapper.tariffsToTariffsDto(tariffRepository.findAll());
    }
}
