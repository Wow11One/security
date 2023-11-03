package com.project.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.security.auth.AuthRequest;
import com.project.security.model.User;
import com.project.security.service.UserService;
import com.project.security.utils.JwtManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminAuthenticationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtManager jwtManager;
    private  User admin;

    @BeforeAll
    public void init() {
        admin = userService.findByUsername("admin").get();
    }

    @Test
    public void IsAuthenticationSuccessful() throws Exception {
        AuthRequest authRequest = new AuthRequest(admin.getUsername(), admin.getPassword());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login").
                        content(objectMapper.writeValueAsString(authRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    //test with bad credentials
    @Test
    public void isAuthenticationFailed() throws Exception {
        AuthRequest authRequest = new AuthRequest("", "evgen");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login").
                        content(objectMapper.writeValueAsString(authRequest)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testThatAdminCanGetAdminData() throws Exception {
        String token = jwtManager.generateToken(admin);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin-info").
                        header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testThatAdminCanGetUserData() throws Exception {
        String token = jwtManager.generateToken(admin);
        mockMvc.perform(MockMvcRequestBuilders.get("/user-info").
                        header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().is2xxSuccessful());
    }

}
