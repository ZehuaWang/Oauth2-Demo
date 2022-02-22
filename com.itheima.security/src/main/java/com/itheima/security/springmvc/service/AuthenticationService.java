package com.itheima.security.springmvc.service;

import com.itheima.security.springmvc.model.AuthenticationRequest;
import com.itheima.security.springmvc.model.UserDto;

public interface AuthenticationService {
    /**
     * User authentication
     * @param authenticationRequest
     * @return
     */
    UserDto authentication(AuthenticationRequest authenticationRequest);
}
