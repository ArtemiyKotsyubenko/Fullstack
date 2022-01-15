package com.example.Fullstack.repository;

import com.example.Fullstack.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Bookings, String> {
    Bookings findByTime(String time);
}
