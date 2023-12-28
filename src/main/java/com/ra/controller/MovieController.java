package com.ra.controller;

import com.ra.dto.request.MovieRequest;
import com.ra.exception.CustomException;
import com.ra.service.interfaces.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/movie")
public class MovieController {
    @Autowired
    private IMovieService movieService ;

    @GetMapping
    public ResponseEntity<?> getALlMovie(@RequestParam(defaultValue = "") String name,
                                         @PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return new ResponseEntity<>(movieService.findAllMovie(name, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id ) throws CustomException {
        return new ResponseEntity<>(movieService.findById(id), HttpStatus.OK) ;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createMovie(@ModelAttribute MovieRequest movieRequest) throws CustomException {
        return new ResponseEntity<>(movieService.save(movieRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateMovie(@PathVariable Long id , @ModelAttribute MovieRequest movieRequest) throws CustomException {
        return new ResponseEntity<>(movieService.update(id, movieRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> changeMovieStatus(@PathVariable Long id,@RequestParam String newStatus) throws CustomException {
        return new ResponseEntity<>(movieService.changeMovieStatus(id, newStatus), HttpStatus.OK);
    }

}
