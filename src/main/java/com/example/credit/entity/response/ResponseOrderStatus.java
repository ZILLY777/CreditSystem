package com.example.credit.entity.response;


import com.example.credit.constants.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseOrderStatus {
    private OrderStatusEnum orderStatus;
}
