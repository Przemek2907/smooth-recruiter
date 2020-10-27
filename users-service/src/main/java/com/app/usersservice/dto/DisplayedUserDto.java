package com.app.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DisplayedUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private RoleDto assignedRole;
    private Boolean isEnabled;
}
