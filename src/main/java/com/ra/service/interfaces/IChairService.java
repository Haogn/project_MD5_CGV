package com.ra.service.interfaces;

import com.ra.dto.request.ChairRequest;
import com.ra.dto.response.ChairResponse;
import com.ra.exception.CustomException;

import java.util.List;

public interface IChairService {
    List<ChairResponse> findAllChair() ;
    ChairResponse findById (Long id) throws CustomException;

    ChairResponse changeStatusChair(Long id ) throws CustomException;



}
