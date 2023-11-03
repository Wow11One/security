package com.project.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    public WeatherService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private ObjectMapper objectMapper;
    @Value("${weather.temperature.api.uri}")
    public String temperatureUri;

    @Value("${weather.temperature.api.uri}")
    public String isDayUri;

    public String getTemperature() {
        return getWeatherInfo(temperatureUri);
    }

    public String getIsDay() {
        return getWeatherInfo(isDayUri);
    }

    private String getWeatherInfo(String uri) {
        String dayJson = new RestTemplate().getForObject(uri, String.class);
        try {
            Map<String, Object> parsedJson = objectMapper.readValue(dayJson, Map.class);
            //different for each request, so it's been removed from response
            parsedJson.remove("generationtime_ms");
            return objectMapper.writeValueAsString(parsedJson);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
