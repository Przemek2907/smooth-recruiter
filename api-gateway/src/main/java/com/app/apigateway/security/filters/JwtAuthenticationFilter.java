package com.app.apigateway.security.filters;

import com.app.apigateway.exception.AppSecurityException;
import com.app.apigateway.security.dto.AuthenticatedPrincipalDataDto;
import com.app.apigateway.security.service.AppTokensService;
import com.app.apigateway.security.dto.AuthenticationDataDto;
import com.app.apigateway.security.dto.TokensDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        Authentication authenticatedPrincipal = null;
        try {
            var authenticationDataDto =
                    new ObjectMapper().readValue(request.getInputStream(), AuthenticationDataDto.class);
            authenticatedPrincipal = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationDataDto.getUsername(),
                    authenticationDataDto.getPassword(),
                    Collections.emptyList()
            ));
        } catch (IOException e) {
            throw new AppSecurityException(e.getMessage());
        }

        return authenticatedPrincipal;
    }

    @Override
    public void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {



        TokensDto tokens = appTokensService.generateTokens(authResult);
        String userEmail = authResult.getName();

        AuthenticatedPrincipalDataDto authenticatedPrincipal = AuthenticatedPrincipalDataDto.builder()
                .userEmail(userEmail)
                .tokens(tokens)
                .role(authResult.getAuthorities().toString())
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(authenticatedPrincipal));
        response.getWriter().flush();
        response.getWriter().close();
    }


}
