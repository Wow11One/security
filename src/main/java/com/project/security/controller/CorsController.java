package com.project.security.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cors")
public class CorsController {

    @CrossOrigin(origins = "*")
    @GetMapping("/any-origin")
    public String anyOrigin() {
        return "available from any origin";
    }

    @CrossOrigin(origins = {"http://localhost:8080"})
    @GetMapping("/localhost-origin")
    public String localhostOrigin() {
        return "available only from localhost";
    }

    @CrossOrigin(origins = "https://stackoverflow.com/")
    @GetMapping("/stackoverflow-origin")
    public String stackoverflowOrigin() {
        return "available only from stackoverflow";
    }

}
