package com.ra.service.interfaces;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.dto.response.BookingDetailResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBookingDetailService {
    Page<BookingDetailResponse> findAllBookingDetail(Pageable pageable) ;
    BookingDetailResponse findById(Long id) throws CustomException;
    BookingDetailResponse save(Long movieId, Long locationId, Long theaterId, Long roomId, Long timesSlotId,Long bookingId,  BookingDetailRequest bookingDetailRequest) throws CustomException;
    BookingDetailResponse cancelBookingDetail(Long id) throws CustomException;
}
