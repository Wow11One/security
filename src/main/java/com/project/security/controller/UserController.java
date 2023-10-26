package com.project.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user-info")
    public String userInfo() {
        return "Data for users and admins";
    }

    @GetMapping("/admin-info")
    public String adminInfo() {
        return "Data for admins";
    }
}
