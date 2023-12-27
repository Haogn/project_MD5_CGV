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
                .timeSlotName(bookingDetail.getChair().getTimeSlot().getName())
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

//    public BookingDetail toEntity(Authentication authentication, BookingDetailRequest bookingDetailRequest) throws CustomException {
//        UserPrincipal userPrincipal  = (UserPrincipal) authentication.getPrincipal();
//        Users users = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new CustomException("User Not Found"));
//        Chair chair = chairRepository.findById(bookingDetailRequest.getChaiId()).orElseThrow(()-> new CustomException("Chair Not Found")) ;
//        TimeSlot timeSlot = timeSlotRepository.findById(bookingDetailRequest.getTimeSlotId()).orElseThrow(()-> new CustomException("TimeSlot Not Found"));
//        Room room = roomRepository.findById(bookingDetailRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
//        Theater theater = theaterRepository.findById(bookingDetailRequest.getTheaterId()).orElseThrow(()-> new CustomException("Theater Not Found"));
//        Location location = locationRepository.findById(bookingDetailRequest.getLocationId()).orElseThrow(()-> new CustomException("Location Not Found"));
//        Movie movie = movieRepository.findById(bookingDetailRequest.getMovieId()).orElseThrow(()-> new CustomException("Movie Not Found"));
//        return BookingDetail.builder()
//                .status(bookingDetailRequest.getStatus())
//                .bookingDate(bookingDetailRequest.getBookingDate())
//                .chair(chair)
//                .movieId(movie.getId())
//                .roomId(room.getId())
//                .theaterId(timeSlot.getId())
//                .theaterId(theater.getId())
//                .users(users)
//                .build();
//    }
}
