package com.twd.springsecurityjwtt.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OurUsersModel {


    private String username;
    private String email;
    private String password;
    private String matchingPassword;
}
