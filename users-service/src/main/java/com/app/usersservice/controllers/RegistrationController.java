package com.app.usersservice.controllers;

import com.app.usersservice.dto.CreateUserDto;
import com.app.usersservice.dto.InviteUserDto;
import com.app.usersservice.dto.ResponseData;
import com.app.usersservice.events.OnInvitationCompleteEvent;
import com.app.usersservice.exceptions.UserServiceException;
import com.app.usersservice.model.InviteUserToken;
import com.app.usersservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/security")
@RestController
public class RegistrationController {

    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long registerUser(@RequestBody CreateUserDto createUserDto) {
        return userService.registerUser(createUserDto);
    }

    @PostMapping("/invite")
    public ResponseEntity<String> initiateRegistration(@RequestBody InviteUserDto inviteUserDto, HttpServletRequest request) {
        try {
            InviteUserToken invitedUserToken = userService.inviteUser(inviteUserDto);
            String applicationUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnInvitationCompleteEvent(invitedUserToken, request.getLocale(), applicationUrl));
            return ResponseEntity.ok("Invitation for user: " +inviteUserDto.getUserFirstName() + " " + inviteUserDto.getUserLastName() + " has been sent and is active for 24 hours");
        }catch (UserServiceException exception) {
            return ResponseEntity.unprocessableEntity().body(exception.getMessage());
        }
    }
}
