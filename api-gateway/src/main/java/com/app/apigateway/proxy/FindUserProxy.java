package com.app.apigateway.proxy;

import com.app.apigateway.dto.GetUserDto;
import com.app.apigateway.dto.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("users-service")
public interface FindUserProxy {

    @GetMapping("/find")
    public ResponseData<List<GetUserDto>> getAllUsers();

    @GetMapping("/find/id/{id}")
    public ResponseData<GetUserDto> getUser(@PathVariable Long id);

    @GetMapping("/find/username/{username}")
    public ResponseData<GetUserDto> getUser(@PathVariable String username);
}
