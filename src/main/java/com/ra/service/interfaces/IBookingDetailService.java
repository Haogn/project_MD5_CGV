package com.ra.service.interfaces;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.dto.response.BookingDetailResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IBookingDetailService {
    Page<BookingDetailResponse> findAllBookingDetail(Pageable pageable) ;
    BookingDetailResponse findById(Long id) throws CustomException;
    BookingDetailResponse save(Authentication authentication,  BookingDetailRequest bookingDetailRequest) throws CustomException;
    BookingDetailResponse changeStatusBookingDetail(Long id) throws CustomException;
    void cancelBookingDetail(Long id) throws CustomException;

}
