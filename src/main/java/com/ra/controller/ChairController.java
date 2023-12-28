package com.ra.controller;

import com.ra.dto.request.ChairRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/chair")
public class ChairController {
    @Autowired
    private IChairService chairService ;

    @GetMapping
    public ResponseEntity<?> getAllChair(){
        return new ResponseEntity<>(chairService.findAllChair(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomException {
        return new ResponseEntity<>(chairService.findById(id), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatusChair(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(chairService.changeStatusChair(id), HttpStatus.OK) ;
    }


}
