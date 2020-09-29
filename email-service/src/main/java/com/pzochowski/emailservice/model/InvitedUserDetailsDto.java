package com.pzochowski.emailservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvitedUserDetailsDto {
    private String userEmail;
    private String userFirstName;
    private String userLastName;
    private String verificationToken;
    private String finishRegistrationLink;
}
