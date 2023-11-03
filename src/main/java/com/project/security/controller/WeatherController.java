package com.project.security.controller;

import com.project.security.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping(value = "/temperature", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getTemperature() {
        return weatherService.getTemperature();
    }

    @GetMapping(value = "/is-day", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getIsDay() {
        return weatherService.getIsDay();
    }
}
