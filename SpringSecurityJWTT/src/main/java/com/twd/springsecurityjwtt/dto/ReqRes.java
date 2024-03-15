package com.twd.springsecurityjwtt.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.twd.springsecurityjwtt.entity.OurUsers;
import com.twd.springsecurityjwtt.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String username;
    private String token;
    private String name;
    private String refreshToken;
    private String expirationTime;
    private String email;
    private String role;
    private String password;
    private String oldPassword;
    private String newPassword;
    private String matchingPassword;
    private boolean enabled;
    private List<Product> product;
    private OurUsers ourUsers;
    private String category;
    private String price ;
    private String description;
    private String Availability;




}
