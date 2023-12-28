package com.ra.mapper;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.dto.response.BookingDetailResponse;
import com.ra.entity.*;
import com.ra.exception.CustomException;
import com.ra.repository.*;
import com.ra.security.user_principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BookingDetailMapper {
    @Autowired
    private IUserRepository userRepository ;

    @Autowired
    private ITimeSlotRepository timeSlotRepository ;

    @Autowired
    private IRoomRepository roomRepository ;

    @Autowired
    private ITheaterRepository theaterRepository ;
    @Autowired
    private ILocationRepository locationRepository ;

    @Autowired
    private IMovieRepository movieRepository ;

    @Autowired
    private IChairRepository chairRepository ;
    public BookingDetailResponse toBookingDetailResponse( BookingDetail bookingDetail) {
        return BookingDetailResponse.builder()
                .id(bookingDetail.getId())
                .customer(bookingDetail.getUsers().getUserName())
                .timeSlotName(bookingDetail.getChair().getRoom().getTimeSlot().getName())
                .roomName(bookingDetail.getChair().getRoom().getName())
                .theaterName(bookingDetail.getChair().getRoom().getTheater().getName())
                .locationName(bookingDetail.getChair().getRoom().getTheater().getLocation().getName())
                .movieName(bookingDetail.getChair().getRoom().getMovie().getName())
                .chairName(bookingDetail.getChair().getName())
                .discount(bookingDetail.getDiscount())
                .subTotal(bookingDetail.getSubTotal())
                .totalAmount(bookingDetail.getTotalAmount())
                .bookingDate(bookingDetail.getBookingDate())
                .status(bookingDetail.getStatus())
                .build();
    }


}
