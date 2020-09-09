package com.app.apigateway.security;

import com.app.apigateway.dto.AuthUserDto;
import com.app.apigateway.dto.ResponseData;
import com.app.apigateway.exception.AppSecurityException;
import com.app.apigateway.security.dto.TokensDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
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

    private static final String REFRESH_TOKEN_HEADER_STRING = "REFRESH_TOKEN";

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, AppTokensService appTokensService) {
        this.authenticationManager = authenticationManager;
        this.appTokensService = appTokensService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        Authentication authentication = null;

        try {
            AuthUserDto authUserDto = new ObjectMapper().readValue(request.getInputStream(), AuthUserDto.class);

            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authUserDto.getUsername(),
                    authUserDto.getPassword(),
                    Collections.emptyList()
            ));
        } catch (Exception e) {
            try {
                response.getWriter().append("Failed to log in - ").append(e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException ioException) {
                throw new AppSecurityException("Failed to write response message");
            }
        }
        return authentication;
    }

    //this method is called when the attemptAuthentication is succesful - we generate the JWT token here
    @Override
    public void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {


        TokensDto jwtTokens = appTokensService.generateTokens(authResult);

        ResponseData bodyWithTokens = ResponseData.builder()
                .data(jwtTokens)
                .build();

        String accessToken = jwtTokens.getAccessToken();
        String refreshToken = jwtTokens.getRefreshToken();

        if ( accessToken == null ) {
            throw new AppSecurityException("Unsuccessful authentication - access token is null exception");
        }

        if ( refreshToken == null ) {
            throw new AppSecurityException("Unsuccessful authentication - refresh token is null exception");
        }

        // HEADER
        response.setHeader(HttpHeaders.AUTHORIZATION, appTokensService.tokenBearer + " " + accessToken);
        response.setHeader(REFRESH_TOKEN_HEADER_STRING, appTokensService.tokenBearer + " " + refreshToken);


        response.getWriter().write(new ObjectMapper().writeValueAsString(bodyWithTokens.getData().toString()));

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
