package com.example.credit.entity.tables;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tariff")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String type;
    private String interestRate;
}
