package com.itheima.security.springmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDto {
    // User information
    public static final String SESSION_USER_KEY = "_user";

    private String id;

    private String username;

    private String password;

    private String fullname;

    private String mobile;

    private Set<String> authorities;
}
