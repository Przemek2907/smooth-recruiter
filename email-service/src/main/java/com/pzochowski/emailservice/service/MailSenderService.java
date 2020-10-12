package com.pzochowski.emailservice.service;
import com.pzochowski.emailservice.model.InvitedUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailSenderService {

    private final MailConfiguration mailSender;
    private static final String SMOOTH_RECRUITER_INVITATION = "SMOOTH RECRUITER INVITATION";


    @Value("${client.application.address}")
    private static String clientUrl;


    public String sendInvitationEmail(InvitedUserDetailsDto invitedUserDetailsDto) {
        String applicationAddress = clientUrl == null ? "http://localhost:4200" : clientUrl + "/invitation/confirm";
        String mailContent = "You have been invited to the SMOOTH RECRUITER. Please click the given link to finish creation of Your account. Your link: " + applicationAddress + "?token=" + invitedUserDetailsDto.getToken();
        mailSender.sendMail(Collections.singletonList(invitedUserDetailsDto.getUserEmail()), null, SMOOTH_RECRUITER_INVITATION, mailContent);
        log.info("================MAIL HAS BEEN SENT==========");
        return "INVITATION MAIL HAS BEEN SENT TO THE USER : " +invitedUserDetailsDto.getUserFirstName().toUpperCase() + " " + invitedUserDetailsDto.getUserLastName().toUpperCase();
    }

}
