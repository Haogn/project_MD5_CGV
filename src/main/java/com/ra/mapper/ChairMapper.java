package com.ra.mapper;

import com.ra.dto.request.ChairRequest;
import com.ra.dto.response.ChairResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.exception.CustomException;
import com.ra.repository.IRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChairMapper {
    @Autowired
    private IRoomRepository roomRepository ;
    public ChairResponse toChairResponse(Chair chair) {
        return ChairResponse.builder()
                .id(chair.getId())
                .name(chair.getName())
                .active(chair.getActive())
                .roomName(chair.getRoom().getName())
                .build();
    }

    public Chair toEntity(ChairRequest chairRequest) throws CustomException {
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
        return Chair.builder()
                .name(chairRequest.getName())
                .active(chairRequest.getActive())
                .room(room)
                .build();
    }

}
