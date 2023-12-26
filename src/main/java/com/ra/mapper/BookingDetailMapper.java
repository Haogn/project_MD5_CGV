package com.ra.mapper;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.dto.response.BookingDetailResponse;
import com.ra.entity.Booking;
import com.ra.entity.BookingDetail;
import com.ra.entity.Chair;
import com.ra.exception.CustomException;
import com.ra.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingDetailMapper {
    @Autowired
    private IBookingRepository bookingRepository ;

    @Autowired
    private ITimeSlotRepository timeSlotRepository ;

    @Autowired
    private IRoomRepository roomRepository ;

    @Autowired
    private ITheaterRepository theaterRepository ;

    @Autowired
    private IMovieRepository movieRepository ;

    @Autowired
    private IChairRepository chairRepository ;
    public BookingDetailResponse toBookingDetailResponse( BookingDetail bookingDetail) {
        return BookingDetailResponse.builder()
                .id(bookingDetail.getId())
                .customer(bookingDetail.getBooking().getUsers().getUserName())
                .timeSlotName(bookingDetail.getChair().getTimeSlot().getName())
                .roomName(bookingDetail.getChair().getRoom().getName())
                .theaterName(bookingDetail.getChair().getRoom().getTheater().getName())
                .locationName(bookingDetail.getChair().getRoom().getTheater().getLocation().getName())
                .movieName(bookingDetail.getChair().getRoom().getMovie().getName())
                .totalAmount(bookingDetail.getChair().getRoom().getMovie().getPrice())
                .bookingDate(bookingDetail.getBookingDate())
                .status(bookingDetail.getStatus())
                .build();
    }

    public BookingDetail toEntity(Long bookingId,  BookingDetailRequest bookingDetailRequest) throws CustomException {
        Chair chair = chairRepository.findById(bookingDetailRequest.getChaiId()).orElseThrow(()-> new CustomException("Chair Not Found")) ;
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(()-> new CustomException("Booking Not Found")) ;
        return BookingDetail.builder()
                .status(bookingDetailRequest.getStatus())
                .bookingDate(bookingDetailRequest.getBookingDate())
                .chair(chair)
                .booking(booking)
                .build();
    }
}
