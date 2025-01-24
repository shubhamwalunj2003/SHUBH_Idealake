package com.shubh.quiz_app.repository;

import org.springframework.stereotype.Repository;

import com.shubh.quiz_app.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import com.shubh.quiz_app.enums.UserRole;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByRole(UserRole role);

    User findFirstByEmail(String email);

    Optional<User> findByEmail(String email);
}

 


