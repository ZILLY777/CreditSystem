package com.example.credit.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoanOrderDTO {
    private long userId;
    private long tariffId;
    private UUID orderId;
}
