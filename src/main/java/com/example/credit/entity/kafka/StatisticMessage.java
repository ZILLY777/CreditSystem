package com.example.credit.entity.kafka;

import com.example.credit.constants.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class StatisticMessage {
    UUID orderId;
    OrderStatusEnum status;
}
