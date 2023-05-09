package com.example.credit.component;

import com.example.credit.entity.kafka.StatisticMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, StatisticMessage> kafkaTemplate;

    public void sendMessage(StatisticMessage msg){
        kafkaTemplate.send("statisticgroup", msg);
    }
}
