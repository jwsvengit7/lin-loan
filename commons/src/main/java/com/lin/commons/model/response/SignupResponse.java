package com.lin.commons.model.response;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SignupResponse {
    private String message;
    private String email;

}
