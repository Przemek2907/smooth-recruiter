package com.app.apigateway.controllers;

import com.app.apigateway.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {


    @GetMapping("/login")
    public ResponseData accessForAll() {
        log.info("WAS I IN THE LOGIN CONTROLLER???");
        return ResponseData.builder().data("ACCESS FOR ALL").build();
    }
}
