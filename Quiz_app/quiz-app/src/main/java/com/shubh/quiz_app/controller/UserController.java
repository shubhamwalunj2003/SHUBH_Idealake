package com.shubh.quiz_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shubh.quiz_app.entities.User;
import com.shubh.quiz_app.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("api/auth")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody  User user) {
        if(userService.hasUserWithEmail(user.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }


        User createUser = userService.createUser(user);
        if (createUser == null) {
            return new ResponseEntity<>("User not created, come again later", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(createUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User dbUser = userService.login(user);

        if(dbUser == null) {
            return new ResponseEntity<>("Wrong credentials", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity<>(dbUser, HttpStatus.OK);
    }
}
