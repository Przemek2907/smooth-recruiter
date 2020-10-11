package com.app.usersservice.events;

import com.app.usersservice.model.InviteUserToken;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnInvitationCompleteEvent extends ApplicationEvent {
    private String applicationUrl;
    private Locale locale;
    private InviteUserToken inviteUserToken;

    public OnInvitationCompleteEvent(InviteUserToken inviteUserToken, Locale locale, String applicationUrl) {
        super(inviteUserToken);

        this.inviteUserToken = inviteUserToken;
        this.locale = locale;
        this.applicationUrl = applicationUrl;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public InviteUserToken getInviteUserToken() {
        return inviteUserToken;
    }

    public void setInviteUserToken(InviteUserToken inviteUserToken) {
        this.inviteUserToken = inviteUserToken;
    }
}
