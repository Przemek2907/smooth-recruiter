package com.app.usersservice.events;

import com.app.usersservice.dto.GetInvitedUserDto;
import com.app.usersservice.dto.InviteUserDto;
import com.app.usersservice.mapper.ModelMapper;
import com.app.usersservice.proxy.SendMailProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CompleteInvitationListener implements ApplicationListener<OnInvitationCompleteEvent> {

    private final SendMailProxy sendMailProxy;

    @Override
    public void onApplicationEvent(OnInvitationCompleteEvent onInvitationCompleteEvent) {
        this.sendInvitation(onInvitationCompleteEvent);
    }

    private void sendInvitation(OnInvitationCompleteEvent invitationEvent) {
        GetInvitedUserDto invitedUserDto = ModelMapper.toInvitedUserDto(invitationEvent.getInviteUserToken());

        sendMailProxy.sendInvitationEmail(invitedUserDto);
    }
}
