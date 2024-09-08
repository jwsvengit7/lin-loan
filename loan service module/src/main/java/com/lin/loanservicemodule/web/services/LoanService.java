package com.lin.loanservicemodule.web.services;

import com.lin.commonsshared.model.request.LoanRequest;
import com.lin.commonsshared.model.response.LinLoanResponse;
import org.springframework.http.ResponseEntity;

public interface LoanService {
    ResponseEntity<LinLoanResponse<?>> generateReport();
    ResponseEntity<LinLoanResponse<?>> allLoan();
    ResponseEntity<LinLoanResponse<?>> getStatistic();

    ResponseEntity<LinLoanResponse<?>> takeLoan(String authorization, LoanRequest request);
}
