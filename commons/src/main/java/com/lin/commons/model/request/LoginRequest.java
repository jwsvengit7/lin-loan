package com.lin.commons.model.request;

import com.lin.commons.validations.UserFields;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    @UserFields
    private String password;

    @UserFields
    private String email;
}
