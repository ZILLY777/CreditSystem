package com.example.credit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TariffDTO {
    private long id;
    private String type;
    @JsonProperty("interest_rate")
    private String interestRate;
}
