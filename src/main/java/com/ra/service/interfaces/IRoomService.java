package com.ra.service.interfaces;

import com.ra.dto.request.RoomRequest;
import com.ra.dto.response.RoomResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomService {
    Page<RoomResponse> findAllRoom(Pageable pageable) ;
    RoomResponse findById(Long id) throws CustomException;
    RoomResponse save(RoomRequest roomRequest) throws CustomException;
    RoomResponse update(Long id , RoomRequest roomRequest) throws CustomException;
    RoomResponse changeStatusRoom(Long id) throws CustomException;
    void delete(Long id) throws CustomException;
}
