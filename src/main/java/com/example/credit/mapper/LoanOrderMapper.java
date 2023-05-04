package com.example.credit.mapper;

import com.example.credit.dto.LoanOrderDTO;
import com.example.credit.entity.tables.LoanOrder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanOrderMapper {
    LoanOrderDTO loanOrderToLoanOrderDto(LoanOrder loanOrder);
    LoanOrder loanOrderDtoToLoanOrder(LoanOrderDTO loanOrderDTO);
    List<LoanOrderDTO> loanOrdersToLoanOrdersDto(List<LoanOrder> loanOrderList);
}
