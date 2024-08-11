package com.lin.commons.model.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor

public class LoginResponse {

    private String email;
    private String name;
    private Long phone;
    private String nin;
    private String jwt;
    private String refreshToken;
    private String date;
}
