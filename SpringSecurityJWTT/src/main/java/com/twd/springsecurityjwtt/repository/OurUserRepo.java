package com.twd.springsecurityjwtt.repository;

import com.twd.springsecurityjwtt.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
    OurUsers findByUsername(String username);

    //OurUsers findByEmail(String email) ;



}
