package com.app.apigateway.security.filters;

import com.app.apigateway.exception.AppSecurityException;
import com.app.apigateway.security.service.AppTokensService;
import com.app.apigateway.security.dto.AuthenticationDataDto;
import com.app.apigateway.security.dto.TokensDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppTokensService appTokensService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AppTokensService appTokensService) {
        this.authenticationManager = authenticationManager;
        this.appTokensService = appTokensService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        try {
            var authenticationDataDto =
                    new ObjectMapper().readValue(request.getInputStream(), AuthenticationDataDto.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDataDto.getUsername(),
                    authenticationDataDto.getPassword(),
                    Collections.emptyList()
            ));
        } catch (Exception e) {
            throw new AppSecurityException(e.getMessage());
        }
    }

    //this method is called when the attemptAuthentication is succesful - we generate the JWT token here
    @Override
    public void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        TokensDto tokens = appTokensService.generateTokens(authResult);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
