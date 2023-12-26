package com.ra.service.interfaces;

import com.ra.dto.request.MovieRequest;
import com.ra.dto.response.MovieResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMovieService {
    Page<MovieResponse> findAllMovie(String name , Pageable pageable) ;
    MovieResponse findById (Long id ) throws CustomException;
    MovieResponse save(MovieRequest movieRequest) throws CustomException;
    MovieResponse update(Long id , MovieRequest movieRequest) throws CustomException;

    MovieResponse changeMovieStatus(Long id, String newStatus) throws CustomException;
    void delete(Long id ) throws CustomException;
}
