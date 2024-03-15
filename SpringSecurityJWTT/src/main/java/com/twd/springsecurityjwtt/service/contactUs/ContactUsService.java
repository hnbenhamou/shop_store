package com.twd.springsecurityjwtt.service.contactUs;

import com.twd.springsecurityjwtt.entity.ContactUs;
import com.twd.springsecurityjwtt.repository.ContactUsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepo contactUsRepository;

    public List<ContactUs> getAllContactUsMessages() {
        return contactUsRepository.findAll();
    }

    public ContactUs saveContactUsMessage(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }
}