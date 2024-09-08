package com.lin.loanservicemodule.loan.entities;

import com.lin.commonsshared.model.enums.LoanStatus;
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
public class LoanTenure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;
    private LocalDateTime dateExpired;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @OneToOne
    private Loan loan;
}
