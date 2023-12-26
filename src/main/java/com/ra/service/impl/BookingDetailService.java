package com.ra.service.impl;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.dto.response.BookingDetailResponse;
import com.ra.entity.*;
import com.ra.exception.CustomException;
import com.ra.mapper.BookingDetailMapper;
import com.ra.repository.*;
import com.ra.service.interfaces.IBookingDetailService;
import com.sun.mail.imap.protocol.ListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingDetailService implements IBookingDetailService {
    @Autowired
    private IBookingDetailRepository bookingDetailRepository;
    @Autowired
    private IBookingRepository bookingRepository;

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

    @Override
    public BookingDetailResponse save(Long bookingId, Long movieId, Long locationId, Long theaterId, Long roomId, Long timesSlotId,  BookingDetailRequest bookingDetailRequest) throws CustomException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new CustomException("Booking of Customer Not Found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new CustomException("Movie Not Found"));
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new CustomException("Location Not Found"));
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new CustomException("Theater Not Found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new CustomException("Room Not Found"));
        TimeSlot timeSlot = timeSlotRepository.findById(timesSlotId).orElseThrow(() -> new CustomException("TimeSlot Not Found"));
        List<BookingDetail> list = bookingDetailRepository.findAll();
        boolean exitsBookingDetail = false;
        for (BookingDetail bd : list) {
            if (bd.getBooking().getId() == booking.getId() &&
                    bd.getChair().getRoom().getMovie().getId() == movie.getId() &&
                    bd.getChair().getRoom().getTheater().getLocation().getId() == location.getId() &&
                    bd.getChair().getRoom().getTheater().getId() == theater.getId() &&
                    bd.getChair().getRoom().getId() == room.getId() &&
                    bd.getChair().getTimeSlot().getId() == timeSlot.getId()) {
                exitsBookingDetail = true;
                throw new CustomException("Exits BookingDetail");
            }
        }
        BookingDetail bookingDetail = new BookingDetail();
        if (!exitsBookingDetail) {
            Chair chair = chairRepository.findById(bookingDetailRequest.getChaiId()).orElseThrow(() -> new CustomException("Chair Not Found"));
            bookingDetail.setBooking(booking);
            bookingDetail.setChair(chair);
            bookingDetail.setBookingDate(bookingDetailRequest.getBookingDate());
            bookingDetail.setStatus(bookingDetailRequest.getStatus());
            bookingDetail.setStatus(bookingDetailRequest.getStatus());
        }
        BookingDetail createBookingDetail = bookingDetailRepository.save(bookingDetail);
        return BookingDetailResponse.builder()
                .id(createBookingDetail.getId())
                .customer(createBookingDetail.getBooking().getUsers().getUserName())
                .timeSlotName(timeSlot.getName())
                .roomName(room.getName())
                .theaterName(theater.getName())
                .locationName(location.getName())
                .movieName(movie.getName())
                .totalAmount(createBookingDetail.getChair().getRoom().getMovie().getPrice())
                .bookingDate(createBookingDetail.getBookingDate())
                .status(createBookingDetail.getStatus())
                .build();
    }

    @Override
    public BookingDetailResponse cancelBookingDetail(Long id) throws CustomException {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id).orElseThrow(() -> new CustomException("BookingDetail Not Found"));
        bookingDetail.setStatus(!bookingDetail.getStatus());
        return bookingDetailMapper.toBookingDetailResponse(bookingDetailRepository.save(bookingDetail));
    }
}
