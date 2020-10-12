package com.pzochowski.emailservice.controller;

import com.pzochowski.emailservice.dto.ResponseData;
import com.pzochowski.emailservice.model.InvitedUserDetailsDto;
import com.pzochowski.emailservice.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SendInvitationController {

    private final MailSenderService mailSenderService;

    @PostMapping("/invitation/send")
    ResponseData<String> sendInvitationEmail(@RequestBody InvitedUserDetailsDto invitedUserDetailsDto) {
        String mailSentMessage = mailSenderService.sendInvitationEmail(invitedUserDetailsDto);
        return ResponseData.<String>builder()
                .data(mailSentMessage)
                .error(null)
                .build();
    }
}
