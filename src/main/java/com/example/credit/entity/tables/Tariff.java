package com.example.credit.entity.tables;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Entity
@Jacksonized
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
