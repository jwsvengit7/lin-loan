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
public class SignUpRequest {
    @UserFields
    private String email;

    @UserFields
    private String firstName;
    @UserFields
    private String password;
    @UserFields
    private String lastName;
    @UserFields
    private Long phone;

    @UserFields
    private String nin;
}
