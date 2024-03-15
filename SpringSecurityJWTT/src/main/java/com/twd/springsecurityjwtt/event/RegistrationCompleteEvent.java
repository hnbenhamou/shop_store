package com.twd.springsecurityjwtt.event;

import com.twd.springsecurityjwtt.entity.OurUsers;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private OurUsers user;
    private String applicationUrl;
    public RegistrationCompleteEvent(OurUsers user, String applicationUrl) {
        super(user);
        this.user=user;
        this.applicationUrl= applicationUrl;
    }
}
