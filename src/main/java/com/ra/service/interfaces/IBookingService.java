package com.ra.service.interfaces;

import com.ra.dto.request.BookingRequest;
import com.ra.dto.response.BookingResponse;
import com.ra.entity.Booking;
import com.ra.exception.CustomException;

import java.util.List;

public interface IBookingService {
    List<BookingResponse> findAllBooking();
    BookingResponse findById(Long idBooking) throws CustomException;
    BookingResponse save(BookingRequest bookingRequest) throws CustomException;
    Booking saveByIdUser(Long  idUser) throws CustomException;
    BookingResponse update(Long idBooking , BookingRequest bookingRequest) throws CustomException;
    void delete(Long idBooking) throws CustomException;
}
