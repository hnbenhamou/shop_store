package com.twd.springsecurityjwtt.controller.ourUser;

import com.twd.springsecurityjwtt.entity.OurUsers;
import com.twd.springsecurityjwtt.service.ourUser.OurUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class OurUserController {

    @Autowired
    private OurUserService userService;

    @GetMapping("/{username}")
    public OurUsers getUserProfile(@PathVariable String username) {
        return userService.getUserByUsername(username);
        }
    @PutMapping("/update/{username}")
    public OurUsers updateUserProfile(@PathVariable String username, @RequestBody OurUsers updatedUser) {
        return userService.updateUserProfile(username, updatedUser);
        }


}
