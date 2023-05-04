package com.example.credit.controller;

import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.entity.response.ResponseData;
import com.example.credit.exceptions.*;
import com.example.credit.service.serviceImpl.OrderServiceImpl;
import com.example.credit.service.serviceImpl.TariffServiceImpl;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseData> getTariffs(){
        ResponseData data = new ResponseData(new ResponseData.Tariffs(tariffService.getAllTariffs()));
        return ResponseEntity.ok(data);
    }

    @PostMapping("order")
    public ResponseEntity<?> postOrder(@RequestBody LoanOrderDTO loanOrderDTO) throws TariffNotFoundException, LoanConsiderationException, LoanAlreadyApproved, TryLaterException {
        return ResponseEntity.ok(orderService.postOrder(loanOrderDTO));
    }

    @GetMapping("getStatusOrder")
    public ResponseEntity<?> getStatusOrder(@RequestParam("orderId") UUID orderId) throws OrderNotFoundException {
        return ResponseEntity.ok(orderService.getOrderStatus(orderId));
    }


    @DeleteMapping("deleteOrder")
    public ResponseEntity<?> deleteOrder(@RequestBody LoanOrderDTO loanOrderDTO) throws OrderNotFoundException, OrderImpossibleToDelete {
        orderService.deleteOrder(loanOrderDTO);
        return ResponseEntity.ok("");
    }



}
