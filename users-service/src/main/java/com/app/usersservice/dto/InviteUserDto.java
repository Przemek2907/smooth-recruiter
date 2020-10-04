package com.app.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteUserDto {

    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private Set<String> roles;

}
