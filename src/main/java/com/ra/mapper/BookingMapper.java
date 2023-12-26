package com.ra.mapper;

import com.ra.dto.request.BookingRequest;
import com.ra.dto.response.BookingResponse;
import com.ra.entity.Booking;
import com.ra.entity.Users;
import com.ra.exception.CustomException;
import com.ra.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    @Autowired
    private IUserRepository userRepository ;
    public BookingResponse toBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .customer(booking.getUsers().getUserName())
                .build();
    }

    public Booking toEntity(BookingRequest bookingRequest) throws CustomException {
        Users users = userRepository.findById(bookingRequest.getUserId()).orElseThrow(()-> new CustomException("User Not Found"));
        return Booking.builder()
                .users(users)
                .build();
    }
}
