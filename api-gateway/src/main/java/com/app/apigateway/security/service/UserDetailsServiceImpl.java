package com.app.apigateway.security.service;

import com.app.apigateway.dto.GetUserDto;
import com.app.apigateway.dto.Role;
import com.app.apigateway.proxy.FindUserProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Qualifier("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final FindUserProxy findUserProxy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        GetUserDto userDto = findUserProxy.getUser(username).getData();

        if (userDto == null) {
            throw new UsernameNotFoundException("Username : " + username + " was not found");
        }

        return new org.springframework.security.core.userdetails.User(
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getIsEnabled(), true, true, true,
                List.of(new SimpleGrantedAuthority(userDto.getRole().toString()))
        );
    }
}
