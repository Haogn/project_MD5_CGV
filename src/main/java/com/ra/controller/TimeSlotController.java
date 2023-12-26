package com.ra.controller;

import com.ra.dto.request.TimeSlotRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/timeslot")
public class TimeSlotController {
    @Autowired
    private ITimeSlotService timeSlotService ;

    @GetMapping
    public ResponseEntity<?> getAllTimeSlot(@PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(timeSlotService.findAllTimeSlot(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return  new ResponseEntity<>(timeSlotService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TimeSlotRequest timeSlotRequest) throws CustomException {
        return new ResponseEntity<>(timeSlotService.save(timeSlotRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TimeSlotRequest timeSlotRequest) throws CustomException {
        return new ResponseEntity<>(timeSlotService.update(id, timeSlotRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws CustomException {
        timeSlotService.delete(id);
        String successMessage = "TimeSlot deleted successfully.";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
