package com.lin.linloanauthmodule.services;

import com.lin.linloanauthmodule.domain.entity.UserOTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class OTPServiceImpl implements OTPService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OTPServiceImpl.class);

    @Override
    public boolean isOTPValid(UserOTP otpUser){
        LOGGER.info("{} ",otpUser.toString());
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime createdDate = otpUser.getExpire();
        Duration duration = Duration.between(createdDate,currentDate);
        long elapseTime = duration.toMinutes();
        long minute = 4;
        LOGGER.info("elapseTime{} ",elapseTime);
        LOGGER.info("minutes{} ",minute);
        return elapseTime > minute;
    }

}
