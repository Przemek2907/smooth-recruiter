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

//TODO co daje enableWebSecurity

// od tej pory ta klasa nadpisuje domysla konfihuracje security springa
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

                //.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

                //.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/user").hasRole("USER")
                .antMatchers("/api/admin").hasRole("ADMIN")
                .antMatchers("/api/user-admin").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()

                .and()
                .addFilter(new com.app.apigateway.security.JwtAuthenticationFilter(authenticationManager(), appTokensService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), appTokensService));
    }

    // in this method I am setting one of a few authentication methods (choosing how users will be looked up during authentication):
    // there are 4
    // an in-memory user store
    // a jdbc-based user store
    // an LDAP-backed user store
    // a custom user details service (used here)

    //enkoduje haslo i podpina uzytkownikow z bazy
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // konfiguracja CORS
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowedOrigins(new ArrayList<>(Arrays.asList("http://localhost:4200")));
//        configuration.setAllowedMethods(new ArrayList<>(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(new ArrayList<>(Arrays.asList("Authorization", "Cache-Control", "Content-Type")));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
