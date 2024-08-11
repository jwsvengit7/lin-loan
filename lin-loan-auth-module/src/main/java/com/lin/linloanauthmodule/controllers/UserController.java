package com.lin.linloanauthmodule.controllers;

import com.lin.commons.model.request.LoginRequest;
import com.lin.commons.model.request.SignUpRequest;
import com.lin.commons.model.request.VerifyOtpRequest;
import com.lin.commons.model.response.LinLoanResponse;
import com.lin.commons.model.response.LoginResponse;
import com.lin.commons.model.response.SignupResponse;
import com.lin.linloanauthmodule.services.UserService;
import jakarta.validation.Valid;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LinLoanResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request){
        return userService.login(request);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<LinLoanResponse<SignupResponse>> createAccount(@RequestBody @Valid SignUpRequest request){
        return userService.register(request);
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<LinLoanResponse<String>> resendOTP(@RequestBody HashMap<String,String> request){
        String email = request.get("email");
        return userService.resendOTP(email);
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<LinLoanResponse<String>> verifyOTP(@RequestBody @Valid VerifyOtpRequest request){
        return userService.verifyOTP(request);
    }
}
