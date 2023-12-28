package com.ra.mapper;

import com.ra.dto.request.ChairRequest;
import com.ra.dto.response.ChairResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.entity.Theater;
import com.ra.entity.TimeSlot;
import com.ra.exception.CustomException;
import com.ra.repository.IRoomRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.repository.ITimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChairMapper {
    @Autowired
    private IRoomRepository roomRepository ;

    @Autowired
    private ITimeSlotRepository timeSlotRepository;

    @Autowired
    private ITheaterRepository theaterRepository ;
    public ChairResponse toChairResponse(Chair chair) {
        return ChairResponse.builder()
                .id(chair.getId())
                .name(chair.getName())
                .active(chair.getActive())
                .roomName(chair.getRoom().getName())
                .timeSlotName(chair.getRoom().getTimeSlot().getName())
                .theaterName(chair.getRoom().getTheater().getName())
                .build();
    }

    public Chair toEntity(ChairRequest chairRequest) throws CustomException {
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
        return Chair.builder()
                .name(chairRequest.getName())
                .active(false)
                .room(room)
                .build();
    }

}
