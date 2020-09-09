package com.app.apigateway.controllers;

import com.app.apigateway.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {


    @GetMapping("/login")
    public ResponseData accessForAll() {
        return ResponseData.builder().data("ACCESS FOR ALL").build();
    }

    @GetMapping("/api/user")
    public ResponseData accessForUser() {
        return ResponseData.builder().data("ACCESS FOR USER").build();
    }

    @GetMapping("/api/admin")
    public ResponseData accessForAdmin() {
        return ResponseData.builder().data("ACCESS FOR ADMIN").build();
    }
}
