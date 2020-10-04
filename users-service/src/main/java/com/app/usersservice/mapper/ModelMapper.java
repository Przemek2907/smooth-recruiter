package com.app.usersservice.mapper;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.dto.GetUserDto;
import com.app.usersservice.model.Role;
import com.app.usersservice.model.User;

import java.util.stream.Collectors;

public interface ModelMapper {

    static GetUserDto toUserDto (User user) {
        return user == null ? null : GetUserDto.builder()
                .id(user.getId())
                .username(user.getUserEmail())
                .password(user.getPassword())
                .role(user.getRole() == null ? null : user.getRole().toString())
                .build();
    }

    static User toUser (CreateUserDto createUserDto) {
        return createUserDto == null ? null : User.builder()
                .userEmail(createUserDto.getUserEmail())
                .password(createUserDto.getPassword())
                .build();
    }
}
