package com.project.security.utils;

import com.project.security.service.WeatherService;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import java.util.Objects;

public class CustomDispatcher extends Dispatcher {
    private final WeatherService weatherService;

    public CustomDispatcher(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        return switch (Objects.requireNonNull(request.getPath())) {
            case "/temperature" -> new MockResponse()
                    .setResponseCode(200)
                    .setBody(weatherService.getTemperature());
            case "/is-day" -> new MockResponse()
                    .setResponseCode(200)
                    .setBody(weatherService.getIsDay());
            default -> new MockResponse().setResponseCode(404);
        };
    }

}
