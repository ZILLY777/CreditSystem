package com.example.credit.entity.response;

import com.example.credit.dto.TariffDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ResponseData {
    private Object data;
    public ResponseData(Object list){
        this.data = list;
    }
    @Getter
    @Setter
    public static class Tariffs{
        private List<TariffDTO> tariffs;

        public Tariffs(List<TariffDTO> tariffs){
            this.tariffs = tariffs;
        }
    }

    @Getter
    @Setter
    public static class OrderId{
        private UUID orderId;
        public OrderId(UUID id){
            this.orderId = id;
        }
    }

}
