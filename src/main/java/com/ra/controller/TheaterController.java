package com.ra.controller;

import com.ra.dto.request.TheaterRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.ITheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/theater")
public class TheaterController {
    @Autowired
    private ITheaterService theaterService ;

    @GetMapping
    public ResponseEntity<?> getAllTheater(@RequestParam(defaultValue = "") String name ,
                                           @PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return new ResponseEntity<>(theaterService.findAllTheater(name, pageable), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomException {
        return new ResponseEntity<>(theaterService.findById(id), HttpStatus.OK) ;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createTheater(@RequestBody TheaterRequest theaterRequest) throws CustomException {
        return new ResponseEntity<>(theaterService.save(theaterRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateTheater(@PathVariable Long id, @RequestBody TheaterRequest theaterRequest) throws CustomException {
        return  new ResponseEntity<>(theaterService.update(id, theaterRequest), HttpStatus.OK) ;
    }


}
