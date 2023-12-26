package com.ra.service.interfaces;

import com.ra.dto.request.LocationRequest;
import com.ra.dto.response.LocationResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILocationService {
    Page<LocationResponse> findAllLocation(String name, Pageable pageable) ;
    LocationResponse findById(Long id) throws CustomException;

    LocationResponse save (LocationRequest locationRequest) throws CustomException;

    LocationResponse update(Long id , LocationRequest locationRequest) throws CustomException;

    void delete(Long id) throws CustomException;
}
