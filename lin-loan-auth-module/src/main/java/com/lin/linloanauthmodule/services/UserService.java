package com.lin.linloanauthmodule.services;

import com.lin.commonsshared.model.request.LoginRequest;
import com.lin.commonsshared.model.request.SignUpRequest;
import com.lin.commonsshared.model.request.VerifyOtpRequest;
import com.lin.commonsshared.model.response.LinLoanResponse;
import com.lin.commonsshared.model.response.LoginResponse;
import com.lin.commonsshared.model.response.SignupResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<LinLoanResponse<LoginResponse>> login(LoginRequest request);
    ResponseEntity<LinLoanResponse<SignupResponse>> register(SignUpRequest request);
    ResponseEntity<LinLoanResponse<String>> resendOTP(String request);
    ResponseEntity<LinLoanResponse<String>> verifyOTP(VerifyOtpRequest request);
}
