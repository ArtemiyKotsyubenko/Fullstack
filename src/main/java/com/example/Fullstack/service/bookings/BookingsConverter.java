package com.example.Fullstack.service.bookings;

import com.example.Fullstack.dto.BookingDTO;
import com.example.Fullstack.entity.Bookings;
import org.springframework.stereotype.Component;

@Component
public class BookingsConverter {
    public Bookings fromBookingsDtoToBookings(BookingDTO usersDto) {
        Bookings bookings = new Bookings();
        bookings.setLogin(usersDto.getLogin());
        bookings.setTime(usersDto.getTime());
        return bookings;
    }

    public BookingDTO fromBookingsToBookingsDto(Bookings bookings) {
        return BookingDTO.builder()
                .login(bookings.getLogin())
                .time(bookings.getTime())
                .build();
    }
}
