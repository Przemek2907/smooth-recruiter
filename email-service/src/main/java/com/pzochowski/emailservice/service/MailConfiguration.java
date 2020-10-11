package com.pzochowski.emailservice.service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@AllArgsConstructor
@Slf4j
@Component
public class MailConfiguration {

    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSmtp;

    @Value("${spring.mail.port}")
    private Integer mailPort;

    @Value("${spring.mail.username}")
    private String mailSenderAddress;

    public MailConfiguration() {
        this.mailSender = new JavaMailSenderImpl();
    }

    @PostConstruct
    public void setAllMailProperties() {
        this.mailSender.setHost(mailHost);
        this.mailSender.setPort(mailPort);
        this.mailSender.setUsername("pzonlinelibrary@gmail.com");
        this.mailSender.setPassword(mailPassword);

        Properties props = this.mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    public void sendMail(List<String> recipientsList, List<String> recipientsCC, String subject, String mailContent) {
    	List<String> hiddenRecipients = new ArrayList<>(Collections.singletonList("przemyslaw.zochowski@gmail.pl"));
        try {
        	
                MimeMessage message = mailSender.createMimeMessage();
                message.setFrom(new InternetAddress(mailSenderAddress));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(",", recipientsList),false));
                //message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(String.join(",", recipientsCC),false));
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(String.join(",", hiddenRecipients),false));
                message.setSubject(subject);
                message.setContent(mailContent, "text/html; charset=UTF-8");
                mailSender.send(message);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
        }
     }
}
