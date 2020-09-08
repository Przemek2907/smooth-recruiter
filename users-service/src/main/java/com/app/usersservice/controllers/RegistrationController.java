package com.app.usersservice.controllers;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/security")
@RestController
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerUser(@RequestBody CreateUserDto createUserDto) {
        return userService.registerUser(createUserDto);
    }

}
