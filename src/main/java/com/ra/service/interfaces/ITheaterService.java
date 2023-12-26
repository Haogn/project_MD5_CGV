package com.ra.service.interfaces;

import com.ra.dto.request.TheaterRequest;
import com.ra.dto.response.TheaterResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITheaterService {
    Page<TheaterResponse> findAllTheater(String name , Pageable pageable) ;

    TheaterResponse findById(Long id ) throws CustomException;

    TheaterResponse save(TheaterRequest theaterRequest) throws CustomException;

    TheaterResponse update(Long id ,TheaterRequest theaterRequest) throws CustomException;

    void delete(Long id) throws CustomException;
}
