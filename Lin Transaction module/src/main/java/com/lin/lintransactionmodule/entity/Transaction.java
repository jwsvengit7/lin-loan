package com.lin.lintransactionmodule.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table
@Entity
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionType;
    private String transactionStatus;
    private String transactionAmount;
    private String transactionDescription;
    private LocalDateTime date;
    private Long userId;

}
