package com.ra.mapper;

import com.ra.dto.request.TimeSlotRequest;
import com.ra.dto.response.TimeSlotResponse;
import com.ra.entity.Room;
import com.ra.entity.TimeSlot;
import com.ra.exception.CustomException;
import com.ra.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    public TimeSlotResponse toTimeSlotMapper(TimeSlot timeSlot) {
        return TimeSlotResponse.builder()
                .id(timeSlot.getId())
                .startTime(timeSlot.getStartTime())
                .endTime(timeSlot.getEndTime())
                .build();
    }

    public TimeSlot toEntity(TimeSlotRequest timeSlotRequest) throws CustomException {
        return TimeSlot.builder()
                .name(timeSlotRequest.getName())
                .startTime(timeSlotRequest.getStartTime())
                .endTime(timeSlotRequest.getEndTime())
                .build();
    }
}
