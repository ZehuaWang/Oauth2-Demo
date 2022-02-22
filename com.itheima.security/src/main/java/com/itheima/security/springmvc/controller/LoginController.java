package com.itheima.security.springmvc.controller;

import com.itheima.security.springmvc.model.AuthenticationRequest;
import com.itheima.security.springmvc.model.UserDto;
import com.itheima.security.springmvc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(value = "/login", produces = "text/plain;charset=utf-8")
    public String login(AuthenticationRequest authenticationRequest, HttpSession session) {
        UserDto userDto = authenticationService.authentication(authenticationRequest);

        // save user into session
        session.setAttribute(UserDto.SESSION_USER_KEY, userDto);

        return userDto.getUsername() + " login success";
    }

    @GetMapping(value = "/logout", produces = {"text/plain; charset=UTF-8"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "log out";
    }

    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=UTF-8"})
    public String r1(HttpSession session) {
        String fullname = null;

        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);

        if (object == null) {
            fullname = "unknown";
        } else {
            UserDto userDto = (UserDto) object;
            fullname = userDto.getFullname();
        }

        return fullname;
    }

    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=UTF-8"})
    public String r2(HttpSession session) {
        String fullname = null;

        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);

        if (object == null) {
            fullname = "unknown";
        } else {
            UserDto userDto = (UserDto) object;
            fullname = userDto.getFullname();
        }

        return fullname;
    }
}
