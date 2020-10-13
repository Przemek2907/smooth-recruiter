package com.pzochowski.emailservice.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MailBuilder {

    private final String backGroundColor = "background-color: #3996b3";
    private final String color = "color: #ffffff";
    private final String padding = "padding: 12px";
    private final String border = "border: 1px solid #3996b3";
    private final String borderRadius = "border-radius: 5px";
    private final String textTransform = "text-transform: uppercase";
    private final String fontSize = "font-size: 14px";
    private final String textDecoration = "text-decoration: none";
    private final String appearance = "appearance: button";
    private final String marginBottom = "margin-bottom: 10px";

    public String buildLinkButton(String buttonText, String link) {
        String aLink = "<a href=\"" + link + "\" style=\"" + buildStyles() + "\">" +buttonText + " </a>";
        return aLink;
    }

    public String buildParagraph(String paragraphText) {
        return  "<p>" + paragraphText + "</p>";
    }

    private String buildStyles () {
        List<String> styleAttributes = new ArrayList<>(Arrays.asList(
                backGroundColor,
                color,
                padding,
                border,
                borderRadius,
                textTransform,
                fontSize,
                textDecoration,
                appearance,
                marginBottom
        ));

        return String.join(";", styleAttributes);
     }
}
