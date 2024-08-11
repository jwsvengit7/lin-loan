package com.lin.linloanprofilemodule.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "PROFILE_TB")
@Getter
@Setter
public class Profile {
    @Id
    private String id;
    private String email;
    private String userId;
    private String role;
    private String firstName;
    private Long phone;
    private String lastName;
    private String userIdFx;
    private String nin;
    private String bvn;
}
