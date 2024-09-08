package com.lin.commonsshared.model.request;


import com.lin.commonsshared.model.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class LoanRequest {
    private double amount;
    private Long userId;
    private String email;
    private LoanType loanType;
    private String category;
}
