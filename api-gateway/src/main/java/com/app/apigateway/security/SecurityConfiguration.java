package com.app.apigateway.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final AppTokensService appTokensService;

    public SecurityConfiguration(
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            AppTokensService appTokensService) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.appTokensService = appTokensService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagger-ui.html").permitAll()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/user").hasRole("USER")
                .antMatchers("/api/admin").hasRole("ADMIN")
                .antMatchers("/api/user-admin").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilter(new com.app.apigateway.security.JwtAuthenticationFilter(authenticationManager(), appTokensService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), appTokensService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // konfiguracja CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(new ArrayList<>(Arrays.asList("http://localhost:4200", "*", "/**")));
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Content-Type");
        configuration.setAllowedMethods(new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
