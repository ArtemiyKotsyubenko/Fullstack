package com.example.Fullstack.service.users;

import com.example.Fullstack.dto.UsersDTO;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UsersService {

    UsersDTO saveUser(UsersDTO usersDto) throws ValidationException;

    void deleteUser(String login);

    UsersDTO findByLogin(String login);

    UsersDTO findByToken(String token);

    List<UsersDTO> findAll();
}