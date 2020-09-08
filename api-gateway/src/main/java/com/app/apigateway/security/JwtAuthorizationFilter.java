package com.app.apigateway.security;

import com.app.apigateway.dto.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
            FilterChain chain) throws IOException {

        try {

            String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (accessToken != null) {
                UsernamePasswordAuthenticationToken auth = appTokensService.parse(accessToken);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(401);
            response.getWriter().write(new ObjectMapper().writeValueAsString(ResponseData.builder().error(e.getMessage()).build()));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
