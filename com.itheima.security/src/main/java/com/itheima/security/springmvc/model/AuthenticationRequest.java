package com.itheima.security.springmvc.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    // Request parameter
    private String username;

    private String password;
}
