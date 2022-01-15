package com.example.Fullstack.service.users;


import com.example.Fullstack.dto.UsersDTO;
import com.example.Fullstack.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersConverter {

    public Users fromUserDTOToUser(UsersDTO usersDto) {
        Users users = new Users();
        users.setLogin(usersDto.getLogin());
        users.setPassword(usersDto.getPassword());
        users.setToken(usersDto.getToken());
        return users;
    }

    public UsersDTO fromUserToUserDTO(Users users) {
        return UsersDTO.builder()
                .login(users.getLogin())
                .password(users.getPassword())
                .token(users.getToken())
                .build();
    }
}
