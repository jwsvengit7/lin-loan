package com.lin.commons.model.request;

import com.lin.commons.validations.UserFields;
import lombok.*;

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
