package com.example.credit.service.serviceImpl;

import com.example.credit.constants.OrderStatusEnum;
import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.entity.tables.LoanOrder;
import com.example.credit.entity.tables.Tariff;
import com.example.credit.exceptions.*;
import com.example.credit.mapper.LoanOrderMapper;
import com.example.credit.repository.repositoryInterfaces.LoanOrderRepository;
import com.example.credit.repository.repositoryInterfaces.TariffRepository;
import com.example.credit.service.serviceInterfaces.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final TariffRepository tariffRepository;
    private final LoanOrderRepository loanOrderRepository;

    private final LoanOrderMapper loanOrderMapper;

    @CircuitBreaker(name = "basicBreaker")
    public UUID postOrder(LoanOrderDTO loanOrderDTO) throws TariffNotFoundException, LoanConsiderationException, LoanAlreadyApproved, TryLaterException {

        Optional<Tariff> tariffOptional = tariffRepository.findTariffById(loanOrderDTO.getTariffId());
        if (tariffOptional.isEmpty()) {
            throw new TariffNotFoundException();
        }
        Optional<LoanOrder> userLoanOrder = loanOrderRepository.findPaymentByUserIdAndTariffId(loanOrderDTO.getUserId(), loanOrderDTO.getTariffId());
        if (userLoanOrder.isEmpty()) {
            LoanOrder entity =  loanOrderMapper.loanOrderDtoToLoanOrder(loanOrderDTO);
            entity.setStatus(OrderStatusEnum.IN_PROGRESS);
            return loanOrderRepository.save(entity).getOrderId();
        }else{
            return handleStatus(userLoanOrder);
        }
    }
    private UUID handleStatus(Optional<LoanOrder> entity) throws LoanConsiderationException, LoanAlreadyApproved, TryLaterException {
        OrderStatusEnum status = entity.get().getStatus();
        switch (status) {
            case IN_PROGRESS -> throw new LoanConsiderationException();
            case APPROVED -> throw new LoanAlreadyApproved();
            case REFUSED -> {
                return handleRefusedStatus(entity);
            }
            default -> throw new UnknownError();
        }
    }
    private UUID handleRefusedStatus(Optional<LoanOrder> entity) throws TryLaterException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if ( TimeUnit.MILLISECONDS.toSeconds(timestamp.getTime() - entity.get().getTimeUpdate().getTime()) < 120) {
            throw new TryLaterException();
        }
        entity.get().setStatus(OrderStatusEnum.IN_PROGRESS);
        LoanOrder loanOrder = loanOrderRepository.save(entity.get());
        return loanOrder.getOrderId();
    }

    @CircuitBreaker(name = "basicBreaker")
    public OrderStatusEnum getOrderStatus(UUID uuid) throws OrderNotFoundException {
        Optional<LoanOrder> order = loanOrderRepository.findOrderByOrderId(String.valueOf(uuid));
        if(order.isEmpty()){
            throw new OrderNotFoundException();
        }
        return order.get().getStatus();
    }

    @CircuitBreaker(name = "basicBreaker")
    public void deleteOrder(LoanOrderDTO loanOrderDTO) throws OrderNotFoundException, OrderImpossibleToDelete {
        Optional<LoanOrder> order = loanOrderRepository.findPaymentByUserIdAndOrderId(loanOrderDTO.getUserId(),
                String.valueOf(loanOrderDTO.getOrderId()));
        if(order.isEmpty()){
            throw new OrderNotFoundException();
        }
        if(!order.get().getStatus().equals(OrderStatusEnum.IN_PROGRESS)) {
            throw new OrderImpossibleToDelete();
        }
        loanOrderRepository.deleteOrder(loanOrderDTO.getUserId(),
                loanOrderDTO.getOrderId().toString());
    }
}
