package com.lin.loanservicemodule.web.controllers;

import com.lin.commonsshared.api.ApiEndpoints;
import com.lin.commonsshared.model.request.LoanRequest;
import com.lin.commonsshared.model.response.LinLoanResponse;
import com.lin.loanservicemodule.web.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoints.LOAN_BASEURL)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanController {
    private final LoanService loanService;

    @GetMapping(ApiEndpoints.LOAN_REPORT)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LinLoanResponse<?>> generateReport(@RequestHeader("Authorization") String authorization){
        return loanService.generateReport();
    }

    @PostMapping(ApiEndpoints.LOAN)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LinLoanResponse<?>> takeLoan(@RequestHeader("Authorization") String authorization,@RequestBody LoanRequest request){
        return loanService.takeLoan(authorization,request);
    }

    @GetMapping(ApiEndpoints.LOAN_STATISTIC)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<LinLoanResponse<?>> getStatistic(@RequestHeader("Authorization") String authorization){
        return loanService.getStatistic();
    }

    @GetMapping(ApiEndpoints.ALL_LOAN)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<LinLoanResponse<?>> allLoan(@RequestHeader("Authorization") String authorization){
        System.out.println(authorization);
        return loanService.allLoan();
    }

}
