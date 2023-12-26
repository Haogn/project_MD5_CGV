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
    public ResponseEntity<?> createMovie(@RequestBody MovieRequest movieRequest) throws CustomException {
        return new ResponseEntity<>(movieService.save(movieRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id , @RequestBody MovieRequest movieRequest) throws CustomException {
        return new ResponseEntity<>(movieService.update(id, movieRequest), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeMovieStatus(@PathVariable Long id,@RequestParam String newStatus) throws CustomException {
        return new ResponseEntity<>(movieService.changeMovieStatus(id, newStatus), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id ) throws CustomException {
        movieService.delete(id);
        String successMessage = "Movie deleted successfully.";
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
