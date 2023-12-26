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
    @Autowired
    private IRoomRepository roomRepository ;
    public TimeSlotResponse toTimeSlotMapper(TimeSlot timeSlot) {
        return TimeSlotResponse.builder()
                .id(timeSlot.getId())
                .starTime(timeSlot.getStarTime())
                .endTime(timeSlot.getEndTime())
                .roomName(timeSlot.getRoom().getName())
                .build();
    }

    public TimeSlot toEntity(TimeSlotRequest timeSlotRequest) throws CustomException {
        Room room = roomRepository.findById(timeSlotRequest.getRoomId()).orElseThrow(() -> new CustomException("Room Not Found"));
        return TimeSlot.builder()
                .name(timeSlotRequest.getName())
                .starTime(timeSlotRequest.getStarTime())
                .endTime(timeSlotRequest.getEndTime())
                .room(room)
                .build();
    }
}
