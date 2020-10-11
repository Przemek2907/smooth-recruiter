package com.app.usersservice.services;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.dto.GetUserDto;
import com.app.usersservice.dto.InviteUserDto;
import com.app.usersservice.exceptions.UserServiceException;
import com.app.usersservice.mapper.ModelMapper;
import com.app.usersservice.model.InviteUserToken;
import com.app.usersservice.model.Role;
import com.app.usersservice.model.User;
import com.app.usersservice.repository.InviteUserRepository;
import com.app.usersservice.repository.RoleRepository;
import com.app.usersservice.repository.UserRepository;
import com.app.usersservice.validator.CreateUserDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InviteUserRepository inviteTokenRepository;

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
        userBeingRegistered.setEnabled(true);
        Optional<Role> userRole =  roleRepository.findByRoleName(createUserDto.getRole());
        userRole.ifPresent(
                (role) -> userBeingRegistered.setRole(userRole.get())
        );

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

    public InviteUserToken inviteUser(InviteUserDto inviteUserDto) {

        if (inviteUserDto == null) {
            throw new UserServiceException("Invite User object has not been initialized");
        }

        if (userRepository.findByUserEmail(inviteUserDto.getUserEmail()).isPresent()) {
            throw new UserServiceException("User with the following email: " + inviteUserDto.getUserEmail() + " already exists in the system.");
        }

        Optional<Role> roleToBeAssigned = roleRepository.findByRoleName(inviteUserDto.getRole());

        if (roleToBeAssigned.isEmpty()) {
            throw new UserServiceException("Role specified for the user does not exist");
        }

        User user = User.builder()
                .userEmail(inviteUserDto.getUserEmail())
                .firstName(inviteUserDto.getUserFirstName())
                .lastName(inviteUserDto.getUserLastName())
                .enabled(false)
                .role(roleToBeAssigned.get())
                .build();

        InviteUserToken inviteUserToken = InviteUserToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .build();

        //userRepository.save(user);
        inviteTokenRepository.save(inviteUserToken);

        return inviteUserToken;
    }


}
