package com.app.usersservice.mapper;

import com.app.usersservice.dto.*;
import com.app.usersservice.model.InviteUserToken;
import com.app.usersservice.model.User;

public interface ModelMapper {

    static GetUserDto toUserDto (User user) {
        return user == null ? null : GetUserDto.builder()
                .id(user.getId())
                .username(user.getUserEmail())
                .password(user.getPassword())
                .isEnabled(user.getEnabled())
                .role(user.getRole() == null ? null : Role.valueOf(user.getRole().getRoleName()))
                .build();
    }

    static User toUser (CreateUserDto createUserDto) {
        return createUserDto == null ? null : User.builder()
                .userEmail(createUserDto.getUserEmail())
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .password(createUserDto.getPassword())
                .build();
    }

    static GetInvitedUserDto toInvitedUserDto (InviteUserToken inviteUserToken) {
        return inviteUserToken == null ? null : GetInvitedUserDto.builder()
                .userEmail(inviteUserToken.getUser().getUserEmail())
                .userFirstName(inviteUserToken.getUser().getFirstName())
                .userLastName(inviteUserToken.getUser().getLastName())
                .token(inviteUserToken.getToken())
                .build();
    }
}
