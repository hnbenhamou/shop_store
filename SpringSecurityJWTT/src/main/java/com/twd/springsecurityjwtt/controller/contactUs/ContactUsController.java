package com.twd.springsecurityjwtt.controller.contactUs;

import com.twd.springsecurityjwtt.entity.ContactUs;
import com.twd.springsecurityjwtt.service.contactUs.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminuser")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @GetMapping("admin/contact-us")
    public List<ContactUs> getAllContactUsMessages() {
        return contactUsService.getAllContactUsMessages();
    }

    @PostMapping("user/contact-us")
    public ContactUs saveContactUsMessage(@RequestBody ContactUs contactUs) {
        return contactUsService.saveContactUsMessage(contactUs);
    }
}
