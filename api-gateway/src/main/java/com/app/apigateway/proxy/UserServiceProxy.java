package com.app.apigateway.proxy;

import com.app.apigateway.dto.GetUserDto;
import com.app.apigateway.dto.ResponseData;
import com.app.apigateway.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("users-service")
public interface UserServiceProxy {

    @GetMapping("/find")
    ResponseData<List<GetUserDto>> getAllUsers();

    @GetMapping("/find/id/{id}")
    ResponseData<GetUserDto> getUser(@PathVariable Long id);

    @GetMapping("/find/username/{username}")
    ResponseData<GetUserDto> getUser(@PathVariable String username);

    @GetMapping("/security/roles/all")
    ResponseData<List<RoleDto>> getAllRoles();

}
