package com.example.credit.entity.response;

import com.example.credit.dto.TariffDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResponseTariffs {
    private List<TariffDTO> tariffs;
}
