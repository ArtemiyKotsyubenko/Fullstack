package com.example.Fullstack.service.bookings;

import com.example.Fullstack.dto.BookingDTO;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface BookingService {


    BookingDTO saveBooking(BookingDTO bookingDto) throws ValidationException;

    void deleteBooking(String time);

    BookingDTO findByTime(String login);

    List<BookingDTO> findAll();

}
