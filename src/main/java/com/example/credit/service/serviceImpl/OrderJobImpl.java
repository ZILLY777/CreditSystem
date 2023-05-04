package com.example.credit.service.serviceImpl;

import com.example.credit.constants.OrderStatusEnum;
import com.example.credit.entity.tables.LoanOrder;
import com.example.credit.repository.repositoryInterfaces.LoanOrderRepository;
import com.example.credit.service.serviceInterfaces.OrderJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderJobImpl implements OrderJob {

    private final LoanOrderRepository loanOrderRepository;

    @Scheduled(fixedRate = 120000)
    public void changeInProgressLoanStatus() {
        List<LoanOrder> ordersList  = loanOrderRepository.findOrdersByOrderStatus(OrderStatusEnum.IN_PROGRESS.toString());
        for (LoanOrder order:
             ordersList) {
            boolean approved = Math.random() < 0.5;
            if (approved){
                loanOrderRepository.updateOrderStatusByOrderId(OrderStatusEnum.REFUSED.toString(), new Timestamp(System.currentTimeMillis()),
                        order.getOrderId().toString());
            }else {
                loanOrderRepository.updateOrderStatusByOrderId(OrderStatusEnum.APPROVED.toString(), new Timestamp(System.currentTimeMillis()),
                        order.getOrderId().toString());
            }

        }

    }
}
