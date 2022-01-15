package com.example.Fullstack.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users {

    @Id
    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String token;
}
