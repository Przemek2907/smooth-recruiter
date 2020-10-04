package com.app.usersservice.dto;

import com.app.usersservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDto {

    private Long id;
    private String username;
    private String password;
    private Set<String> roles;
}
