package com.twd.springsecurityjwtt.event.listener;

import com.twd.springsecurityjwtt.entity.OurUsers;
import com.twd.springsecurityjwtt.event.RegistrationCompleteEvent;
import com.twd.springsecurityjwtt.service.ourUser.OurUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j

public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {


    @Autowired
    private OurUserService ourUserService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Create the verification token for the user
        OurUsers user = event.getUser();
        String token = UUID.randomUUID().toString();
        ourUserService.saveVerificationTokenForUser(token,user);

        //Send mail to the user
        String url  = event.getApplicationUrl()
                + "/auth/verifyRegistration?token="
                + token;

        //SendVerificationEmail
        log.info("Click the link to verify your account: {}",
                url);


    }
}
