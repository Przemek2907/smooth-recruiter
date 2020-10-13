package com.pzochowski.emailservice.service;
import com.pzochowski.emailservice.model.InvitedUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailSenderService {

    private final MailConfiguration mailSender;
    private final MailBuilder mailBuilder;
    private static final String SMOOTH_RECRUITER_INVITATION = "SMOOTH RECRUITER INVITATION";

    @Value("${client.application.address}")
    private String clientUrl;


    @Async
    public CompletableFuture<String> sendInvitationEmail(InvitedUserDetailsDto invitedUserDetailsDto) {
        System.out.println(clientUrl);
        String appLink = clientUrl == null ? "http://localhost:4200/invitation/confirm" : clientUrl;
        String fullLink = appLink + "?token=" + invitedUserDetailsDto.getToken();
        String mailText = mailBuilder.buildParagraph("You have been invited to the SMOOTH RECRUITER. Please click the given link to finish creation of Your account. Click here to set up Your account: ");
        String mailLink = mailBuilder.buildLinkButton("SET UP MY ACCOUNT", fullLink);
        String mailContent = mailText + mailLink;
        mailSender.sendMail(Collections.singletonList(invitedUserDetailsDto.getUserEmail()), null, SMOOTH_RECRUITER_INVITATION, mailContent);
        log.info("================MAIL HAS BEEN SENT==========");
        log.info(appLink);
        return CompletableFuture.completedFuture("INVITATION MAIL HAS BEEN SENT TO THE USER : " +invitedUserDetailsDto.getUserFirstName().toUpperCase() + " " + invitedUserDetailsDto.getUserLastName().toUpperCase());
    }

}
