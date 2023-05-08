package com.example.credit.controller;

import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.entity.response.Generic.ResponseData;
import com.example.credit.entity.response.ResponseOrderId;
import com.example.credit.entity.response.ResponseOrderStatus;
import com.example.credit.entity.response.ResponseTariffs;
import com.example.credit.exceptions.*;
import com.example.credit.service.serviceImpl.OrderServiceImpl;
import com.example.credit.service.serviceImpl.TariffServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("loan-service")
public class LoanController {
    private final TariffServiceImpl tariffService;
    private final OrderServiceImpl orderService;

    @GetMapping("getTariffs")
    @CircuitBreaker(name = "basicBreaker")
    public ResponseEntity<ResponseData<ResponseTariffs>> getTariffs(){
        ResponseData<ResponseTariffs> data = ResponseData.wrap(new ResponseTariffs(tariffService.getAllTariffs()));
        return ResponseEntity.ok(data);
    }

    @PostMapping("order")
    public ResponseEntity<ResponseData<ResponseOrderId>> postOrder(@RequestBody LoanOrderDTO loanOrderDTO) throws TariffNotFoundException, LoanConsiderationException, LoanAlreadyApproved, TryLaterException {
        ResponseData<ResponseOrderId> data = ResponseData.wrap(new ResponseOrderId(orderService.postOrder(loanOrderDTO)));
        return ResponseEntity.ok(data);
    }

    @GetMapping("getStatusOrder")
    public ResponseEntity<ResponseData<ResponseOrderStatus>> getStatusOrder(@RequestParam("orderId") UUID orderId) throws Exception {
        ResponseData<ResponseOrderStatus> data = ResponseData.wrap(new ResponseOrderStatus(orderService.getOrderStatus(orderId)));
        return ResponseEntity.ok(data);
    }


    @DeleteMapping("deleteOrder")
    public ResponseEntity<String> deleteOrder(@RequestBody LoanOrderDTO loanOrderDTO) throws OrderNotFoundException, OrderImpossibleToDelete {
        orderService.deleteOrder(loanOrderDTO);
        return ResponseEntity.ok("");
    }



}
