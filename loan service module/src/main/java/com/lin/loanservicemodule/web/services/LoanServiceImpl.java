package com.lin.loanservicemodule.web.services;

import com.lin.commonsshared.jwt.JwtServiceInfo;
import com.lin.commonsshared.model.request.LoanRequest;
import com.lin.commonsshared.model.response.LinLoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final JwtServiceInfo jwtServiceInfo;


    @Override
    public ResponseEntity<LinLoanResponse<?>> generateReport() {
        return null;
    }



    @Override
    public ResponseEntity<LinLoanResponse<?>> allLoan() {
        return null;
    }

    @Override
    public ResponseEntity<LinLoanResponse<?>> getStatistic() {
        return null;
    }

    @Override
    public ResponseEntity<LinLoanResponse<?>> takeLoan(String authorization, LoanRequest request) {
        boolean validate = validateAccess(authorization,request.getEmail());
        if(validate) {
            return null;
        }
        return null;
    }

    private boolean validateAccess(String authorization,String username){
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
           return jwtServiceInfo.isTokenValidApi(token,username);
        }
        return false;
    }
}
