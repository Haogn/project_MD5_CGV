package com.ra.controller;

import com.ra.dto.request.BookingRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/booking")
public class BookingController {
    @Autowired
    private IBookingService bookingService ;

    @GetMapping
    public ResponseEntity<?> getAllBooking(){
        return new ResponseEntity<>(bookingService.findAllBooking(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return  new ResponseEntity<>(bookingService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingRequest bookingRequest) throws CustomException {
        return new ResponseEntity<>(bookingService.save(bookingRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BookingRequest bookingRequest) throws CustomException {
        return new ResponseEntity<>(bookingService.update(id, bookingRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws CustomException {
        bookingService.delete(id);
        String successMessage = "Category deleted successfully.";
        return new ResponseEntity<>(successMessage, HttpStatus.OK) ;
    }
}
