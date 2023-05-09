package com.example.credit.entity.tables;

import com.example.credit.constants.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="loan_order")
public class LoanOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @Positive
    private long userId;

    private long tariffId;

    private BigDecimal creditRating;

    private OrderStatusEnum status;

    private Timestamp timeInsert;

    private Timestamp timeUpdate;

    private Boolean provided;

}
