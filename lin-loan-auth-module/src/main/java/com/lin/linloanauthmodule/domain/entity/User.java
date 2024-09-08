package com.lin.linloanauthmodule.domain.entity;

import com.lin.commonsshared.model.enums.UserStatus;
import com.lin.commonsshared.model.enums.UserType;
import com.lin.commonsshared.model.request.ProfileDtoData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_TB")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Long phone;

    private String profileDtoData;


}
