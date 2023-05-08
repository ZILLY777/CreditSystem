package com.example.credit.service.serviceInterfaces;

import com.example.credit.constants.OrderStatusEnum;
import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.entity.tables.LoanOrder;
import com.example.credit.exceptions.LoanAlreadyApproved;
import com.example.credit.exceptions.LoanConsiderationException;
import com.example.credit.exceptions.TariffNotFoundException;
import com.example.credit.exceptions.TryLaterException;

import java.util.UUID;

public interface OrderService {
    UUID postOrder(LoanOrderDTO loanOrderDTO) throws Exception;

    private UUID handleStatus(LoanOrder entity) throws Exception {
        return handleRefusedStatus(entity);
    }

    private UUID handleRefusedStatus(LoanOrder entity) throws Exception {
        return entity.getOrderId();
    }

    OrderStatusEnum getOrderStatus(UUID uuid) throws Exception;
    void deleteOrder(LoanOrderDTO loanOrderDTO) throws Exception;
}
