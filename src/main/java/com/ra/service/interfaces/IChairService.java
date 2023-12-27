package com.ra.service.interfaces;

import com.ra.dto.request.ChairRequest;
import com.ra.dto.response.ChairResponse;
import com.ra.exception.CustomException;

import java.util.List;

public interface IChairService {
    List<ChairResponse> findAllChair() ;
    ChairResponse findById (Long id) throws CustomException;
    List<ChairResponse> save(ChairRequest chairRequest) throws CustomException;
    ChairResponse update(Long id , ChairRequest chairRequest) throws CustomException;
    ChairResponse changeStatusChair(Long id ) throws CustomException;
    void delete(Long id) throws CustomException;


}
