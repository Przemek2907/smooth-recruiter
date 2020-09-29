package com.app.usersservice.controllers;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.dto.InviteUserDto;
import com.app.usersservice.dto.ResponseData;
import com.app.usersservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/invite")
    public ResponseEntity<InviteUserDto> initiateRegistration(@RequestBody InviteUserDto inviteUserDto) {
        return new ResponseEntity(userService.inviteUser(inviteUserDto), HttpStatus.CREATED);
    }
}
