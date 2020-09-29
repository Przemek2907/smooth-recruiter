package com.pzochowski.emailservice.service;

import com.pzochowski.emailservice.model.InvitedUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class MailSenderService {

    private final JavaMailSender mailSender;

    private void sendSystemInvitationEmail(InvitedUserDetailsDto invitedUserDetailsDto, String mailContent) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(invitedUserDetailsDto.getUserEmail()));
        //message.setRecipient(Message.RecipientType.CC, new );

        message.setContent(mailContent, "text/html; charset=UTF-8");
    }
}
