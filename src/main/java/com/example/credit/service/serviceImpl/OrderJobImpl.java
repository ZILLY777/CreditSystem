package com.example.credit.service.serviceImpl;

import com.example.credit.component.KafkaProducer;
import com.example.credit.constants.OrderStatusEnum;
import com.example.credit.entity.kafka.StatisticMessage;
import com.example.credit.entity.tables.LoanOrder;
import com.example.credit.repository.repositoryInterfaces.LoanOrderRepository;
import com.example.credit.service.serviceInterfaces.OrderJob;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderJobImpl implements OrderJob {

    private final KafkaProducer kafkaProducer;

    private final LoanOrderRepository loanOrderRepository;

    @Override
    @Async
    @Scheduled(fixedRate = 120000)
    public void changeInProgressLoanStatus() {
        List<LoanOrder> ordersList  = loanOrderRepository.findOrdersByOrderStatus(OrderStatusEnum.IN_PROGRESS.toString());
        for (LoanOrder order:
             ordersList) {
            UUID orderId = order.getOrderId();
            boolean approved = Math.random() < 0.5;
            if (approved){
                loanOrderRepository.updateOrderStatusByOrderId(OrderStatusEnum.REFUSED.toString(), new Timestamp(System.currentTimeMillis()),
                        orderId.toString());
                kafkaProducer.sendMessage(new StatisticMessage(orderId, OrderStatusEnum.REFUSED ));


            }else {
                loanOrderRepository.updateOrderStatusByOrderId(OrderStatusEnum.APPROVED.toString(), new Timestamp(System.currentTimeMillis()),
                        orderId.toString());
                kafkaProducer.sendMessage(new StatisticMessage(orderId, OrderStatusEnum.APPROVED ));
            }
            loanOrderRepository.updateOrderProvidedByOrderId(orderId.toString(), true);
        }

    }

    @Override
    public void checkProvidedLoanStatus() {
        List<LoanOrder> ordersList  = loanOrderRepository.findOrdersByOrderProvided(false);
        for (LoanOrder order:
                ordersList) {
            UUID orderId = order.getOrderId();
            kafkaProducer.sendMessage(new StatisticMessage(orderId, order.getStatus()));
            loanOrderRepository.updateOrderProvidedByOrderId(orderId.toString(), true);
        }
    }

}
