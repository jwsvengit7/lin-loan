package com.lin.loanservicemodule.loan.entities;

import com.lin.commonsshared.model.enums.LoanType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;
    @Enumerated(EnumType.ORDINAL)
    private LoanType loanTypeNo;

    private String email;
    private String userId;
    private String fullName;
    private String phone;
    private String bvn;
    private double amount;
    private String transId;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "loan",orphanRemoval = true)
    private LoanTenure tenure;

}
