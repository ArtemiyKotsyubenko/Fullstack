package com.example.Fullstack.service.bookings;

import com.example.Fullstack.dto.BookingDTO;
import com.example.Fullstack.entity.Bookings;
import com.example.Fullstack.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultBookingService implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingsConverter bookingsConverter;




    @Override
    public BookingDTO saveBooking(BookingDTO bookingDto) throws ValidationException {
        validateBookingDTO(bookingDto);
        Bookings savedBooking = bookingRepository.save(bookingsConverter.fromBookingsDtoToBookings(bookingDto));
        return bookingsConverter.fromBookingsToBookingsDto(savedBooking);
    }

    private void validateBookingDTO(BookingDTO bookingDTO) throws ValidationException {
        if (isNull(bookingDTO)) {
            throw new ValidationException("Object booking is null");
        }
        if (isNull(bookingDTO.getLogin()) || bookingDTO.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
    }


    @Override
    public BookingDTO findByTime(String time) {
        Bookings bookings = bookingRepository.findByTime(time);
        if (bookings != null) {
            return bookingsConverter.fromBookingsToBookingsDto(bookings);
        }
        return null;
    }



    @Override
    public void deleteBooking(String time) {
        bookingRepository.delete(bookingRepository.findByTime(time));

    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(bookingsConverter::fromBookingsToBookingsDto)
                .collect(Collectors.toList());
    }
}
