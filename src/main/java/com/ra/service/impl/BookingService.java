package com.ra.service.impl;

import com.ra.dto.request.BookingRequest;
import com.ra.dto.response.BookingResponse;
import com.ra.entity.Booking;
import com.ra.entity.Users;
import com.ra.exception.CustomException;
import com.ra.mapper.BookingMapper;
import com.ra.repository.IBookingRepository;
import com.ra.repository.IUserRepository;
import com.ra.service.interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private IBookingRepository bookingRepository ;
    @Autowired
    private IUserRepository userRepository ;
    @Autowired
    private BookingMapper bookingMapper ;

    @Override
    public List<BookingResponse> findAllBooking() {
        List<Booking> list = bookingRepository.findAll();
        return list.stream().map(item -> bookingMapper.toBookingResponse(item)).collect(Collectors.toList());
    }

    @Override
    public BookingResponse findById(Long idBooking) throws CustomException {
        Booking booking = bookingRepository.findById(idBooking).orElseThrow(()-> new CustomException("Booking Not Found"));
        return bookingMapper.toBookingResponse(booking);
    }



    @Override
    public Booking saveByIdUser(Long idUser) throws CustomException {
        Users users = userRepository.findById(idUser).orElseThrow(()-> new CustomException("User Not Found")) ;
        Booking booking = new Booking() ;
       if (users != null) {
           booking.setUsers(users);
       }
       return bookingRepository.save(booking);
    }

    @Override
    public BookingResponse save(BookingRequest bookingRequest) throws CustomException {
        Users users = userRepository.findById(bookingRequest.getUserId()).orElseThrow(()-> new CustomException("User Not Found")) ;
        Booking booking = new Booking() ;
        if (users != null) {
             booking = bookingRepository.save(bookingMapper.toEntity(bookingRequest));
        }
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public BookingResponse update(Long idBooking, BookingRequest bookingRequest) throws CustomException {
        Booking booking = bookingRepository.findById(idBooking).orElseThrow(()-> new CustomException("Booking Not Found"));
        Users users = userRepository.findById(bookingRequest.getUserId()).orElseThrow(()-> new CustomException("User Not Found")) ;

        booking.setId(idBooking);
        booking.setUsers(users);
        bookingRepository.save(booking);
        return bookingMapper.toBookingResponse(booking);
    }

    @Override
    public void delete(Long idBooking) throws CustomException {
        Booking booking = bookingRepository.findById(idBooking).orElseThrow(()-> new CustomException("Booking Not Found"));
        if (booking != null) {
            bookingRepository.deleteById(idBooking);
        }
    }
}
