package com.example.credit.repository.repositoryInterfaces;


import com.example.credit.entity.tables.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffRepository {

    List<Tariff> findAll();
    Tariff save(Tariff tariff);
    Optional<Tariff> findTariffById(long id);

}
