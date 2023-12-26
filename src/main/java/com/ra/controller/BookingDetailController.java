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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/booking-detail")
public class BookingDetailController {

    @Autowired
    private IBookingDetailService bookingDetailService ;

    @GetMapping
    public ResponseEntity<?> getAllBookingDetail(@PageableDefault(size = 4, page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        return new ResponseEntity<>(bookingDetailService.findAllBookingDetail(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/{bookingId}/{movieId}/{locationId}/{theaterId}/{roomId}/{timesSlotId}")
    public ResponseEntity<?> create(@PathVariable Long bookingId,
                                    @PathVariable Long movieId,
                                    @PathVariable Long locationId,
                                    @PathVariable Long theaterId,
                                    @PathVariable Long roomId,
                                    @PathVariable Long timesSlotId, @RequestBody BookingDetailRequest bookingDetailRequest) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.save(bookingId,movieId,locationId,theaterId,roomId,timesSlotId,bookingDetailRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(bookingDetailService.cancelBookingDetail(id), HttpStatus.OK);
    }

}
