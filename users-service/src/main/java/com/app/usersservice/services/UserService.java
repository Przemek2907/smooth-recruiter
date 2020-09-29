package com.app.usersservice.services;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.dto.GetUserDto;
import com.app.usersservice.dto.InviteUserDto;
import com.app.usersservice.exceptions.UserServiceException;
import com.app.usersservice.mapper.ModelMapper;
import com.app.usersservice.model.User;
import com.app.usersservice.repository.UserRepository;
import com.app.usersservice.validator.CreateUserDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public Long registerUser(CreateUserDto createUserDto) {

        var createUserValidator = new CreateUserDtoValidator();
        var errors = createUserValidator.validate(createUserDto);

        if (!errors.isEmpty()) {
            var errorMessage = errors.entrySet().stream()
                    .map(
                            x -> x.getKey() + ": " + x.getValue()
                    ).collect(Collectors.joining(","));
            throw new UserServiceException(errorMessage);
        }

        if (userRepository.findByUserEmail(createUserDto.getUserEmail()).isPresent()) {
            throw new UserServiceException("User with the following login : " + createUserDto.getUserEmail() + " already exists");
        }

        User userBeingRegistered = ModelMapper.toUser(createUserDto);

        // TODO should I encrypt the password here before saving it into the db
        userBeingRegistered.setPassword(passwordEncoder.encode(userBeingRegistered.getPassword()));
        return userRepository.save(userBeingRegistered).getId();

    }

    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(ModelMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public GetUserDto getUserById(Long id) {
        return ModelMapper.toUserDto(userRepository.findById(id).orElseThrow(
                () -> new UserServiceException("Could not find user with id : " + id)
        ));
    }

    public GetUserDto getUserByUserName(String name) {
        return ModelMapper.toUserDto(userRepository.findByUserEmail(name).orElseThrow(
                () -> new UserServiceException("Could not find user with login : " + name)
        ));
    }

    public InviteUserDto inviteUser(InviteUserDto inviteUserDto) {

        return inviteUserDto;
    }
}
