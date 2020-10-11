package com.pzochowski.emailservice.service;
import com.pzochowski.emailservice.model.InvitedUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;


@RequiredArgsConstructor
@Service
public class MailSenderService {

    private final MailConfiguration mailSender;
    private static final String SMOOTH_RECRUITER_INVITATION = "SMOOTH RECRUITER INVITATION";

    @Value("${clientApp.host.url}")
    private static String clientUrl;


    public String sendInvitationEmail(InvitedUserDetailsDto invitedUserDetailsDto) {
        String applicationAddress = clientUrl + "/invitation/confirm";
        String mailContent = "You have been invited to the SMOOTH RECRUITER. Please click the given link to finish creation of Your account. Your link: " + applicationAddress + "/?token=" + invitedUserDetailsDto.getVerificationToken();
        mailSender.sendMail(Collections.singletonList(invitedUserDetailsDto.getUserEmail()), null, SMOOTH_RECRUITER_INVITATION, mailContent);
        return "Mail has been sent";
    }

}
