package com.app.apigateway.security.dto;

import com.app.apigateway.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedPrincipalDataDto {
    private String userEmail;
    private TokensDto tokens;
    private String role;
}
