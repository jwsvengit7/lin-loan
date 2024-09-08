package com.lin.linloanauthmodule.controllers;

import com.lin.commonsshared.api.ApiEndpoints;
import com.lin.commonsshared.model.request.LoginRequest;
import com.lin.commonsshared.model.request.SignUpRequest;
import com.lin.commonsshared.model.request.VerifyOtpRequest;
import com.lin.commonsshared.model.response.LinLoanResponse;
import com.lin.commonsshared.model.response.LoginResponse;
import com.lin.commonsshared.model.response.SignupResponse;
import com.lin.linloanauthmodule.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(ApiEndpoints.AUTH_BASEURL)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(ApiEndpoints.AUTH_LOGIN)
    public ResponseEntity<LinLoanResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request){
        return userService.login(request);
    }
    @PostMapping(ApiEndpoints.AUTH_SIGNUP)
    public ResponseEntity<LinLoanResponse<SignupResponse>> createAccount(@RequestBody @Valid SignUpRequest request){
        return userService.register(request);
    }
    @PostMapping(ApiEndpoints.AUTH_RESEND_OTP)
    public ResponseEntity<LinLoanResponse<String>> resendOTP(@RequestBody HashMap<String,String> request){
        final String email = request.get("email");
        return userService.resendOTP(email);
    }
    @PostMapping(ApiEndpoints.AUTH_VERIFY_OTP)
    public ResponseEntity<LinLoanResponse<String>> verifyOTP(@RequestBody @Valid VerifyOtpRequest request){
        return userService.verifyOTP(request);
    }
}
