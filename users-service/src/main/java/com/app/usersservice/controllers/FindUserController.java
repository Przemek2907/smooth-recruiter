package com.app.usersservice.controllers;

import com.app.usersservice.dto.GetUserDto;
import com.app.usersservice.dto.ResponseData;
import com.app.usersservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/find")
@RestController
public class FindUserController {

    private final UserService userService;

    @GetMapping
    public ResponseData<List<GetUserDto>> getAllUsers() {
        return ResponseData
                .<List<GetUserDto>>builder()
                .data(userService.getAllUsers())
                .build();
    }

    @GetMapping("/id/{id}")
    public ResponseData<GetUserDto> getUser(@PathVariable Long id) {
        return ResponseData
                .<GetUserDto>builder()
                .data(userService.getUserById(id))
                .build();
    }

    @GetMapping("/username/{username}")
    public ResponseData<GetUserDto> getUser(@PathVariable String username) {
        return ResponseData
                .<GetUserDto>builder()
                .data(userService.getUserByUserName(username))
                .build();
    }
}
