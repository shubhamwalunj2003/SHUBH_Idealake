package com.shubh.quiz_app.services;

import com.shubh.quiz_app.entities.User;

public interface UserService {

    User createUser(User user);

    Boolean hasUserWithEmail(String email) ;

    User login(User user);
}
