package com.example.Fullstack.repository;

import com.example.Fullstack.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByLogin(String login);

    Users findByToken(String token);
}
