package com.example.credit.dto;

import lombok.Data;

@Data
public class TariffDTO {
    private long id;
    private String type;
    private String interestRate;
}
