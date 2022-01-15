package com.example.Fullstack.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
public class Bookings {

    @Column
    private String login;

    @Id
    @Column
    private String time;
}
