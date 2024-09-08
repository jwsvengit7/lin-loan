package com.lin.commonsshared.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class OtpData{
    private String email;
    private Long id;
    private String otp;

    public OtpData(String otp, String email, Long id) {
        this.email=email;
        this.otp=otp;
        this.id=id;
    }
}
