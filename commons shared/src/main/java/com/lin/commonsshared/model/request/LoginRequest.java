package com.lin.commonsshared.model.request;

import com.lin.commonsshared.validations.UserFields;
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
