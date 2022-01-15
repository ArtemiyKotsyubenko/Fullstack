package com.example.Fullstack.controller;

import com.example.Fullstack.dto.BookingDTO;
import com.example.Fullstack.service.bookings.BookingService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
@Log
@CrossOrigin
public class BookingsController {
    private final BookingService bookingService;

    @PostMapping("/book")
    public BookingDTO saveBooking(@RequestBody BookingDTO bookingDTO) throws ValidationException {
        log.info("Handling save booking: " + bookingDTO);
        return bookingService.saveBooking(bookingDTO);
    }

    @GetMapping("/getSlots")
    public List<BookingDTO> findAllBookings() {
        log.info("Get all bookings");
        return bookingService.findAll();
    }

    @PostMapping("/unbook")
    public ResponseEntity<Void> deleteBooking(@RequestParam Map<String, String> params) {
        log.info("Handling delete booking: " + params);
        var time = params.get("time");
        var login = params.get("login");
        var timeDto = bookingService.findByTime(time);
        if (timeDto != null && timeDto.getLogin().equals(login)) {
            bookingService.deleteBooking(time);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
