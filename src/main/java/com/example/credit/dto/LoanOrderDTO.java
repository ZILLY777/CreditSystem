package com.example.credit.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;


import java.util.UUID;

@Data
public class LoanOrderDTO {
    @Positive
    private long userId;
    private long tariffId;
    private UUID orderId;
}
