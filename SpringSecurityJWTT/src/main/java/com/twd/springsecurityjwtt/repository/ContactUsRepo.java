package com.twd.springsecurityjwtt.repository;

import com.twd.springsecurityjwtt.entity.ContactUs;
import com.twd.springsecurityjwtt.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepo extends JpaRepository<ContactUs, Integer> {
}
