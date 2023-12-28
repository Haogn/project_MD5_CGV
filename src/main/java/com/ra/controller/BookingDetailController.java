package com.ra.controller;

import com.ra.dto.request.BookingDetailRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IBookingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/booking")
public class BookingDetailController {

    @Autowired
    private IBookingDetailService bookingDetailService ;

    @GetMapping
    public ResponseEntity<?> getAllBookingDetail(@PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(bookingDetailService.findAllBookingDetail(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBooking(Authentication authentication, @RequestBody BookingDetailRequest bookingDetailRequest) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.save(authentication, bookingDetailRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.changeStatusBookingDetail(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) throws CustomException {
        bookingDetailService.cancelBookingDetail(id);
        String successMessage = "booking cancel success" ;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }


}
