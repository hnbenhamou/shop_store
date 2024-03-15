package com.twd.springsecurityjwtt.service;

import com.twd.springsecurityjwtt.dto.ReqRes;
import com.twd.springsecurityjwtt.entity.OurUsers;
import com.twd.springsecurityjwtt.entity.VerificationToken;
import com.twd.springsecurityjwtt.repository.OurUserRepo;
import com.twd.springsecurityjwtt.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private OurUserRepo ourUserRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public ReqRes signUp(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUsers = new OurUsers();
            ourUsers.setUsername(registrationRequest.getUsername());
            ourUsers.setEmail(registrationRequest.getEmail());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUsers.setRole(registrationRequest.getRole());
            OurUsers ourUserResult = ourUserRepo.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId() > 0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(ReqRes signinRequest) {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
            var user = ourUserRepo.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: " + user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest) {
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
        OurUsers users = ourUserRepo.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.getToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }



}
