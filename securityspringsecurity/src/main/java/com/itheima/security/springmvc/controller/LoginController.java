package com.itheima.security.springmvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @RequestMapping(value = "/login-success", produces = {"text/plain; charset=UTF-8"})
    public String loginSuccess() {
        return "login successfully";
    }

    @GetMapping(value = "/r/r1", produces = {"text/plain; charset=UTF-8"})
    public String r1() {
        return "Visit resource 1";
    }

    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=UTF-8"})
    public String r2() {
        return "Visit resource 2";
    }
}