package com.lin.linloanauthmodule.services;

import com.lin.linloanauthmodule.domain.entity.User;
import com.lin.linloanauthmodule.domain.entity.UserOTP;

public interface OTPService {
    boolean isOTPValid(UserOTP otpUser);
    UserOTP generateOTP(User user, UserOTP otp);
}
