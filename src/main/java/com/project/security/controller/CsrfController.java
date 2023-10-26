package com.project.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "*")
@Controller
public class CsrfController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/csrf-success")
    public String getCsrfPage() {
        return "csrf-success";
    }

}
