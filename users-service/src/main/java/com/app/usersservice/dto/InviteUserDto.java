package com.app.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteUserDto {

    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String role;

}
