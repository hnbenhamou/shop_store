package com.twd.springsecurityjwtt.controller;

import com.twd.springsecurityjwtt.dto.ReqRes;
import com.twd.springsecurityjwtt.entity.OurUsers;
import com.twd.springsecurityjwtt.entity.VerificationToken;
import com.twd.springsecurityjwtt.event.RegistrationCompleteEvent;
import com.twd.springsecurityjwtt.service.AuthService;
import com.twd.springsecurityjwtt.service.ourUser.OurUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private OurUserService ourUserService;

    @PostMapping("/signup")
    public String signUp(@RequestBody ReqRes signUpRequest, final HttpServletRequest request){
        OurUsers ourUsers = authService.signUp(signUpRequest).getOurUsers();
        publisher.publishEvent(new RegistrationCompleteEvent(
                ourUsers,
                applicationUrl(request)
        ));
        return "success";
    }




    @GetMapping("/verifyRegistration")
    public String verifyRegisration(@RequestParam("token") String token){
        String result = ourUserService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User Verifies Successfuly";
        }
        return "Bad User";
    }

    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request){
        VerificationToken verificationToken
                = ourUserService.generateNewVerificationToken(oldToken);
        OurUsers user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent";

    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ReqRes reqRes, HttpServletRequest request){
        Optional<OurUsers> user = ourUserService.findUserByEmail(reqRes.getEmail());
        String url = "";
        if (user.isPresent()){
            String token = UUID.randomUUID().toString();
            ourUserService.createPasswordResetTokenForUser(user.get(),token);
            url = passwordResetTokenMail(user,applicationUrl(request), token);
        }
        return url;
    }



    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody ReqRes reqRes){
        String result = ourUserService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<OurUsers> user = ourUserService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            ourUserService.changePassword(user.get(), reqRes.getNewPassword());
            return "Password Reset Successfully";

        }else {
            return "Invalid Token";
        }

    }

    private String passwordResetTokenMail(Optional<OurUsers> user, String applicationUrl, String token) {
        //Send mail to the user
        String url  = applicationUrl
                + "/auth/savePassword?token="
                + token;

        //SendVerificationEmail
        log.info("Click the link to reset your Password: {}",
                url);
        return url;
    }

    private void resendVerificationTokenMail(OurUsers user, String applicationUrl, VerificationToken verificationToken) {
        //Send mail to the user
        String url  = applicationUrl
                + "/auth/verifyRegistration?token="
                + verificationToken;

        //SendVerificationEmail
        log.info("Click the link to verify your account: {}",
                url);
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }

    @PostMapping("/signin")
    public ResponseEntity<ReqRes> signIn(@RequestBody ReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
