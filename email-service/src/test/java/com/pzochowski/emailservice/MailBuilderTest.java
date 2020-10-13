package com.pzochowski.emailservice;

import com.pzochowski.emailservice.service.MailBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MailBuilderTest {

    @Autowired
    MailBuilder mailBuilder;

    @Test
    public void shouldBuildFormHtml() {
        System.out.println(mailBuilder.buildLinkButton("MY BUTTON", "SOME LINK"));
    }
}
