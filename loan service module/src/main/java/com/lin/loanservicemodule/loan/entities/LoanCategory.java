package com.lin.loanservicemodule.loan.entities;

import com.lin.commonsshared.model.enums.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoanCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double maxAmount;
    private double minAmount;

    private String description;
    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
