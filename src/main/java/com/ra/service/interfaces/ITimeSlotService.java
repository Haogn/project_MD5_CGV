package com.ra.service.interfaces;

import com.ra.dto.request.TimeSlotRequest;
import com.ra.dto.response.TimeSlotResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITimeSlotService {
    Page<TimeSlotResponse> findAllTimeSlot(Pageable pageable) ;

    TimeSlotResponse findById(Long id) throws CustomException;

    TimeSlotResponse save(TimeSlotRequest timeSlotRequest) throws CustomException;

    TimeSlotResponse update(Long id , TimeSlotRequest timeSlotRequest) throws CustomException;
    void delete(Long id) throws CustomException;
}
