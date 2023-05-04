package com.example.credit.repository.repositoryInterfaces;

import com.example.credit.entity.tables.LoanOrder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


public interface LoanOrderRepository {

    List<LoanOrder> findAll();
    LoanOrder save(LoanOrder loanOrder);
    List<LoanOrder> findOrdersByUserId(long id);
    Optional<LoanOrder> findPaymentByUserIdAndTariffId(long userId, long tariffId );

    Optional<LoanOrder> findPaymentByUserIdAndOrderId(long userId, String orderId );

    Optional<LoanOrder> findOrderByOrderId(String uuid);

    boolean deleteOrder(long userId, String orderId);

    List<LoanOrder> findOrdersByOrderStatus(String status);

    void updateOrderStatusByOrderId(String status, Timestamp updateTime, String orderId);
}
