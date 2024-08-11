package com.lin.linloanauthmodule.domain.repository;

import com.lin.linloanauthmodule.domain.entity.User;
import com.lin.linloanauthmodule.domain.entity.UserOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOTPRepository extends JpaRepository<UserOTP,Long> {
    UserOTP findUserOTPByUser(User user);
    UserOTP findByUser(User user);
    UserOTP findByOtpAndUser(String otp,User user);
}
