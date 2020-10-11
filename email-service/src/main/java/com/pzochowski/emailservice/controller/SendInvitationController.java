package com.pzochowski.emailservice.controller;

import com.pzochowski.emailservice.model.InvitedUserDetailsDto;
import com.pzochowski.emailservice.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SendInvitationController {

    private final MailSenderService mailSenderService;

    @PostMapping("/invitation/send")
    ResponseEntity<String> sendInvitationEmail(
            @RequestBody InvitedUserDetailsDto invitedUserDetailsDto
            ) {

        return ResponseEntity.ok().body(mailSenderService.sendInvitationEmail(invitedUserDetailsDto));
    };

}
