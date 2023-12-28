package com.ra.service.impl;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.dto.response.BookingDetailResponse;
import com.ra.entity.*;
import com.ra.exception.CustomException;
import com.ra.mapper.BookingDetailMapper;
import com.ra.repository.*;
import com.ra.security.user_principal.UserPrincipal;
import com.ra.service.interfaces.IBookingDetailService;
import com.sun.mail.imap.protocol.ListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingDetailService implements IBookingDetailService {
    @Autowired
    private IBookingDetailRepository bookingDetailRepository;

    @Autowired
    private IUserRepository userRepository ;

    @Autowired
    private ITimeSlotRepository timeSlotRepository;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private ITheaterRepository theaterRepository;

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private IChairRepository chairRepository;
    @Autowired
    private BookingDetailMapper bookingDetailMapper;

    @Autowired
    private ILocationRepository locationRepository;

    @Override
    public Page<BookingDetailResponse> findAllBookingDetail(Pageable pageable) {
        Page<BookingDetail> bookingDetails = bookingDetailRepository.findAll(pageable);
        return bookingDetails.map(item -> bookingDetailMapper.toBookingDetailResponse(item));
    }

    @Override
    public BookingDetailResponse findById(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("BookingDetail Not Found"));
        return bookingDetailMapper.toBookingDetailResponse(bookingDetail);
    }

//    @Override
//    public BookingDetailResponse save(Authentication authentication, BookingDetailRequest bookingDetailRequest) throws CustomException {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        Users users = userRepository.findById(userPrincipal.getId()).orElseThrow(()-> new CustomException("User Not Found"));
//        Movie movie = movieRepository.findById(bookingDetailRequest.getMovieId()).orElseThrow(() -> new CustomException("Movie Not Found"));
//        Location location = locationRepository.findById(bookingDetailRequest.getLocationId()).orElseThrow(() -> new CustomException("Location Not Found"));
//        Theater theater = theaterRepository.findById(bookingDetailRequest.getTheaterId()).orElseThrow(() -> new CustomException("Theater Not Found"));
//        Room room = roomRepository.findById(bookingDetailRequest.getRoomId()).orElseThrow(() -> new CustomException("Room Not Found"));
//        Chair chair = chairRepository.findById(bookingDetailRequest.getChaiId()).orElseThrow(() -> new CustomException("Chair Not Found"));
//        TimeSlot timeSlot = timeSlotRepository.findById(bookingDetailRequest.getTimeSlotId()).orElseThrow(() -> new CustomException("TimeSlot Not Found"));
//        List<BookingDetail> list = bookingDetailRepository.findAll();
//        // tách ra hàm riêng
//        boolean exitsBookingDetail = false;
//        for (BookingDetail bd : list) {
//            if (bd.getUsers().getId() == users.getId() &&
//                    bd.getChair().getRoom().getMovie().getId() == movie.getId() &&
//                    bd.getChair().getRoom().getTheater().getLocation().getId() == location.getId() &&
//                    bd.getChair().getRoom().getTheater().getId() == theater.getId() &&
//                    bd.getChair().getRoom().getId() == room.getId() &&
//                    bd.getChair().getTimeSlot().getId() == timeSlot.getId() &&
//                    bd.getChair().getId() == chair.getId()) {
//                exitsBookingDetail = true;
//                throw new CustomException("Exits BookingDetail");
//            }
//        }
//        BookingDetail bookingDetail = new BookingDetail();
//        if (!exitsBookingDetail) {
//            bookingDetail.setChair(chair);
//            bookingDetail.setBookingDate(bookingDetailRequest.getBookingDate());
//            bookingDetail.setStatus(bookingDetailRequest.getStatus());
//        }
//        BookingDetail createBookingDetail = bookingDetailRepository.save(bookingDetail);
//        return BookingDetailResponse.builder()
//                .id(createBookingDetail.getId())
//                .timeSlotName(timeSlot.getName())
//                .roomName(room.getName())
//                .theaterName(theater.getName())
//                .locationName(location.getName())
//                .movieName(movie.getName())
//                .totalAmount(createBookingDetail.getChair().getRoom().getMovie().getPrice())
//                .bookingDate(createBookingDetail.getBookingDate())
//                .status(createBookingDetail.getStatus())
//                .build();
//    }

    @Override
    public BookingDetailResponse save(Authentication authentication, BookingDetailRequest bookingDetailRequest) throws CustomException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Users users = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new CustomException("User Not Found"));

        // Continue with creating a new booking detail
        Movie movie = movieRepository.findById(bookingDetailRequest.getMovieId())
                .orElseThrow(() -> new CustomException("Movie Not Found"));
        Theater theater = theaterRepository.findById(bookingDetailRequest.getTheaterId())
                .orElseThrow(() -> new CustomException("Theater Not Found"));
        Room room = roomRepository.findById(bookingDetailRequest.getRoomId())
                .orElseThrow(() -> new CustomException("Room Not Found"));
        Chair chair = chairRepository.findById(bookingDetailRequest.getChaiId())
                .orElseThrow(() -> new CustomException("Chair Not Found"));
        TimeSlot timeSlot = timeSlotRepository.findById(bookingDetailRequest.getTimeSlotId())
                .orElseThrow(() -> new CustomException("TimeSlot Not Found"));
        List<BookingDetail> list = bookingDetailRepository.findAll();
        for (BookingDetail bd : list) {
            if (bd.getUsers().getId() == users.getId() &&
                    bd.getChair().getRoom().getMovie().getId() == movie.getId() &&
                    bd.getChair().getRoom().getTheater().getId() == theater.getId() &&
                    bd.getChair().getRoom().getId() == room.getId() &&
                    bd.getChair().getRoom().getTimeSlot().getId() == timeSlot.getId() &&
                    bd.getChair().getId() == chair.getId()) {
                throw new CustomException("Exits BookingDetail");
            }
        }


        Integer count = 0 ;
        for (BookingDetail bd : list) {
            if (bd.getStatus()){
                count += 1 ;
            }
        }

        if (count > 0 && count <= 10) {
            users.setMemberLevers(MemberLevelName.BRONZE);
        } else if (count > 10 && count <= 20) {
            users.setMemberLevers(MemberLevelName.SILVER);
        } else if (count > 20 && count <= 30) {
            users.setMemberLevers(MemberLevelName.GOLD);
        } else {
            users.setMemberLevers(MemberLevelName.PLATINUM);
        }
        users.setScorePoints(count);

        Double discount = 0.0;
        Double subTotal = 0.0;
        Double totalAmount = 0.0 ;
        switch (users.getMemberLevers().name()) {
            case "BRONZE":
                discount =( 0.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount ;
                break;
            case "SILVER":
                discount =( 3.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount ;
                break;
            case "GOLD":
                discount =( 5.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount ;
                break;
            case "PLATINUM":
                discount =( 10.0 / 100) * movie.getPrice();
                subTotal = movie.getPrice();
                totalAmount = subTotal - discount ;
                break;

        }
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setUsers(users);
        bookingDetail.setChair(chair);
        bookingDetail.setDiscount(discount);
        bookingDetail.setSubTotal(subTotal);
        bookingDetail.setTotalAmount(totalAmount);
        chair.setActive(true);
        bookingDetail.setBookingDate(new Date());
        bookingDetail.setStatus(false);


        BookingDetail createBookingDetail = bookingDetailRepository.save(bookingDetail);

        return BookingDetailResponse.builder()
                .id(createBookingDetail.getId())
                .customer(userPrincipal.getUsername())
                .chairName(chair.getName())
                .timeSlotName(timeSlot.getName())
                .roomName(room.getName())
                .theaterName(theater.getName())
                .locationName(theater.getLocation().getName())
                .movieName(movie.getName())
                .discount(createBookingDetail.getDiscount())
                .subTotal(createBookingDetail.getSubTotal())
                .totalAmount(createBookingDetail.getTotalAmount())
                .bookingDate(createBookingDetail.getBookingDate())
                .status(createBookingDetail.getStatus())
                .build();
    }

    @Override
    public BookingDetailResponse changeStatusBookingDetail(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("BookingDetail Not Found"));
        if (!bookingDetail.getStatus()) {
            bookingDetail.setStatus(true);
        }
        bookingDetailRepository.save(bookingDetail);
        return bookingDetailMapper.toBookingDetailResponse(bookingDetail);
    }


    @Override
    public void cancelBookingDetail(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("BookingDetail Not Found"));
       if (!bookingDetail.getStatus()) {
           roomRepository.deleteById(id);
       }
    }


}
