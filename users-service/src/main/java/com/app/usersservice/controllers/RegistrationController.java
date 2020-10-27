package com.app.usersservice.controllers;

import com.app.usersservice.dto.*;
import com.app.usersservice.events.OnInvitationCompleteEvent;
import com.app.usersservice.exceptions.UserServiceException;
import com.app.usersservice.model.InviteUserToken;
import com.app.usersservice.services.RoleService;
import com.app.usersservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/security")
@RestController
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerUser(@RequestBody CreateUserDto createUserDto) {
        return userService.registerUser(createUserDto);
    }

    @PostMapping("/invite")
    public ResponseData<String> initiateRegistration(@RequestBody InviteUserDto inviteUserDto, HttpServletRequest request) {
        log.info("WAS IN IN USERS-SERVICE INVITE CONTROLLER?");
        System.out.println(inviteUserDto);
        try {
            InviteUserToken invitedUserToken = userService.inviteUser(inviteUserDto);
            String applicationUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnInvitationCompleteEvent(invitedUserToken, request.getLocale(), applicationUrl));
            return ResponseData.<String>builder()
                    .data(("Invitation for user " +inviteUserDto.getUserFirstName() + " " + inviteUserDto.getUserLastName() + " has been sent and is active for 24 hours"))
                    .build();
        }catch (UserServiceException exception) {
            return ResponseData.<String>builder()
                    .error(exception.getMessage())
                    .build();
        }
    }

    @GetMapping("/roles/all")
    public ResponseData<List<RoleDto>> getAllRoles () {
        return ResponseData
                .<List<RoleDto>>builder()
                .data(roleService.getAllRoles())
                .build();
    }
}
