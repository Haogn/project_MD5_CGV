package com.ra.controller;

import com.ra.dto.request.RoomRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/room")
public class RoomController {
    @Autowired
    private IRoomService roomService ;

    @GetMapping
    public ResponseEntity<?> getAllRoom(@PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return new ResponseEntity<>(roomService.findAllRoom(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest roomRequest) throws CustomException {
        return new ResponseEntity<>(roomService.save(roomRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RoomRequest roomRequest) throws CustomException {
        return new ResponseEntity<>(roomService.update(id, roomRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeStatusRoom(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(roomService.changeStatusRoom(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) throws CustomException {
        roomService.delete(id);
        String successMessage = "Room deleted successfully.";
        return new ResponseEntity<>(successMessage, HttpStatus.OK) ;
    }
}
