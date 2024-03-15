package com.twd.springsecurityjwtt.service.ourUser;

import com.twd.springsecurityjwtt.entity.OurUsers;
import com.twd.springsecurityjwtt.entity.PasswordResetToken;
import com.twd.springsecurityjwtt.entity.VerificationToken;
import com.twd.springsecurityjwtt.repository.OurUserRepo;
import com.twd.springsecurityjwtt.repository.PasswordResetTokenRepository;
import com.twd.springsecurityjwtt.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class OurUserService {


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;


    @Autowired
    private OurUserRepo userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public OurUsers getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public OurUsers updateUserProfile(String username, OurUsers updatedUser) {
        OurUsers existingUser = userRepository.findByUsername(username);

        if (existingUser != null) {
            // Update user's personal information
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());

            // Save the updated user to the database
            userRepository.save(existingUser);
        }

        return existingUser;
    }


    public void saveVerificationTokenForUser(String token, OurUsers user) {
        VerificationToken verificationToken = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }


    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "invalid";
        }
        OurUsers user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";



    }

    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public Optional<OurUsers> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUser(OurUsers user, String token) {
        PasswordResetToken passwordResetToken;
        passwordResetToken = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);

    }


    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken
                = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return "invalid";
        }
        OurUsers user = passwordResetToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((passwordResetToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid";
    }

    public Optional<OurUsers> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    public void changePassword(OurUsers user, String newPassword) {

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        //user.setPassword(passwordEncoder.encode(newPassword));
        //userRepository.save(user);
    }


}
