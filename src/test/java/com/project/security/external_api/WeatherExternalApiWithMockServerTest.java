package com.project.security.external_api;

import com.project.security.service.WeatherService;
import com.project.security.utils.CustomDispatcher;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherExternalApiWithMockServerTest {

    @Autowired
    private WeatherService weatherService;
    private Dispatcher dispatcher;
    private WebClient webClient;
    public static MockWebServer server;

    @BeforeAll
    public void init() throws IOException {
        dispatcher = new CustomDispatcher(weatherService);

        server = new MockWebServer();
        server.start(8080);
        server.setDispatcher(dispatcher);

        webClient = WebClient.create(String.format("http://%s:8080", server.getHostName()));

    }

    @ParameterizedTest
    @MethodSource("provideEndpoints")
    public void testThatTemperatureEquals(String endpoint) {
        String mockResponse = webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertEquals(mockResponse, weatherService.getTemperature());
    }

    private Stream<Arguments> provideEndpoints() throws IOException {
        return Stream.of(
                Arguments.of("/temperature"),
                Arguments.of("/is-day")
        );
    }


}
