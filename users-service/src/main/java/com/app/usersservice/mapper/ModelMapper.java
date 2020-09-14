package com.app.usersservice.mapper;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.dto.GetUserDto;
import com.app.usersservice.model.User;

public interface ModelMapper {

    static GetUserDto toUserDto (User user) {
        return user == null ? null : GetUserDto.builder()
                .id(user.getId())
                .username(user.getUserEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    static User toUser (CreateUserDto createUserDto) {
        return createUserDto == null ? null : User.builder()
                .userEmail(createUserDto.getUserEmail())
                .password(createUserDto.getPassword())
                .role(createUserDto.getRole())
                .build();
    }
}
