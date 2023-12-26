package com.ra.controller;

import com.ra.dto.request.LocationRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/location")
public class LocationController {
    @Autowired
    private ILocationService locationService ;

    @GetMapping
    public ResponseEntity<?> getAllLocation(@RequestParam(defaultValue = "") String name,
                                            @PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(locationService.findAllLocation(name, pageable), HttpStatus.OK) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(locationService.findById(id), HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody LocationRequest locationRequest) throws CustomException {
        return new ResponseEntity<>(locationService.save(locationRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id , @RequestBody LocationRequest locationRequest) throws CustomException {
        return new ResponseEntity<>(locationService.update(id, locationRequest), HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) throws CustomException {
        locationService.delete(id);
        String successMessage = "Location deleted successfully.";
        return new ResponseEntity<>(successMessage, HttpStatus.OK) ;
    }
}
