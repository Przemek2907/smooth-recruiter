package com.app.usersservice.proxy;

import com.app.usersservice.dto.GetInvitedUserDto;
import com.app.usersservice.dto.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mail-service")
public interface SendMailProxy {

    @PostMapping("/invitation/send")
    ResponseData<String> sendInvitationEmail(@RequestBody GetInvitedUserDto inviteUserDto);
}
