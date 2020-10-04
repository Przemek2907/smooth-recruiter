package com.app.usersservice.dto;

import com.app.usersservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    private Long id;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String password;
    private String passwordConfirmation;
    private String role;
}
