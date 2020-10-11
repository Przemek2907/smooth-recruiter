package com.app.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInvitedUserDto {
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String token;
}
