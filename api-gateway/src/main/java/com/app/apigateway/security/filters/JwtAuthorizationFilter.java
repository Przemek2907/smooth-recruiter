package com.app.apigateway.security.filters;

import com.app.apigateway.exception.AppSecurityException;
import com.app.apigateway.security.service.AppTokensService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final AppTokensService appTokensService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AppTokensService appTokensService) {
        super(authenticationManager);
        this.appTokensService = appTokensService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) {

        try {

            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (accessToken != null) {
                UsernamePasswordAuthenticationToken auth = appTokensService.parse(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw new AppSecurityException(e.getMessage());
        }
    }
}
