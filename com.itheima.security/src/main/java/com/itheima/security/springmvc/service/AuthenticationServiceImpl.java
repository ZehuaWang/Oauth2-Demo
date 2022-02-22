package com.itheima.security.springmvc.service;

import com.itheima.security.springmvc.model.AuthenticationRequest;
import com.itheima.security.springmvc.model.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private Map<String, UserDto> userMap = new HashMap<>();
    {
        Set<String> authorities1 = new HashSet<>();
        authorities1.add("p1");
        userMap.put("eric", new UserDto("1010", "eric", "123", "eric", "3439882666", authorities1));

        Set<String> authorities2 = new HashSet<>();
        authorities2.add("p2");
        userMap.put("king", new UserDto("1011", "king", "123", "king", "3439882666", authorities2));
    }

    @Override
    public UserDto authentication(AuthenticationRequest authenticationRequest) {
        // check if user is valid
        if (authenticationRequest == null
            || StringUtils.isEmpty(authenticationRequest.getUsername())
            || StringUtils.isEmpty(authenticationRequest.getPassword())) {
            throw new RuntimeException("User name or password is empty");
        }

        UserDto userDto = getUserDto(authenticationRequest.getUsername());

        if (userDto == null) {
            throw new RuntimeException("User does not exist");
        }

        if (!authenticationRequest.getPassword().equals(userDto.getPassword())) {
            throw new RuntimeException("User name or password is wrong");
        }

        return userDto;
    }

    public UserDto getUserDto(String username) {
        return userMap.get(username);
    }
}