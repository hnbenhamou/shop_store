package com.twd.springsecurityjwtt.controller.email;

import com.twd.springsecurityjwtt.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/email/send")
    public String sendEmail(@RequestParam("to") String to , @RequestParam("subject") String subject , @RequestParam("text") String text) {

        emailService.sendEmail(to, subject, text);

        return "Email sent successfully!";
    }
}
