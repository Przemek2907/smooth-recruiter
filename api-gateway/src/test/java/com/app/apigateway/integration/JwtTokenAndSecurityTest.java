package com.app.apigateway.integration;


import com.app.apigateway.dto.AuthUserDto;
import com.app.apigateway.proxy.UserServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class JwtTokenAndSecurityTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceProxy findUserProxy;


    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Testing if user can login if his credentials are in the DB")
    public void whenCorrectCredentials_thenLoginSuccessful() throws Exception {

        AuthUserDto authUserDto = AuthUserDto.builder()
                .username("przemek")
                .password("qwerty")
                .build();

        this.mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(authUserDto))
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User with username elton has been registered"));

    }
}
