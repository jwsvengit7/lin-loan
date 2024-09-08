package com.lin.commonsshared.model.request;

import com.lin.commonsshared.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ProfileDtoData {
    private String bvn;
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private Long phone;
    private String nin;
    private UserType role;
}
