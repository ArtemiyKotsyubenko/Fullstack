package com.example.Fullstack.controller;

import com.example.Fullstack.dto.UsersDTO;
import com.example.Fullstack.service.users.UsersService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log
@CrossOrigin
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<String> saveUsers(@RequestBody UsersDTO usersDto) throws ValidationException {

        if (usersService.findByLogin(usersDto.getLogin()) == null) {


            Map<String, Object> tokenData = new HashMap<>();
            tokenData.put("clientType", "user");
            tokenData.put("username", usersDto.getLogin());
            tokenData.put("token_create_date", new Date().getTime());

            JwtBuilder jwtBuilder = Jwts.builder();
            jwtBuilder.setClaims(tokenData);
            String key = usersDto.getPassword();

            String token = jwtBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
            usersDto.setToken(token);
            usersService.saveUser(usersDto);
            log.info("Handling save users: " + usersDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            log.info("User with login  " + usersDto.getLogin() + " already exists");
            return new ResponseEntity<>(null, HttpStatus.IM_USED);
        }

    }


    @PostMapping("/signin")
    public ResponseEntity<String> signInByPass(@RequestParam Map<String, String> params) {

        var login = params.get("login");
        var password = params.get("password");
        log.info("Handling find by login request: " + login);
        var userDto = usersService.findByLogin(login);
        var response = new ResponseEntity(userDto.getToken(), HttpStatus.OK);
        if (userDto == null || !userDto.getPassword().equals(password)) {
            response = new ResponseEntity(userDto.getToken(), HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> signInByToken(@RequestParam Map<String, String> params) {
        var token = params.get("token");
        log.info("Handling find by token request: " + token);
        var userDto = usersService.findByToken(token);
        if (userDto != null) {
            return new ResponseEntity<>(userDto.getToken(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
