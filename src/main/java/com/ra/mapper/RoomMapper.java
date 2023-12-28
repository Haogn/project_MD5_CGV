package com.ra.mapper;

import com.ra.dto.request.RoomRequest;
import com.ra.dto.response.RoomResponse;
import com.ra.entity.*;
import com.ra.exception.CustomException;
import com.ra.repository.IChairRepository;
import com.ra.repository.IMovieRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.repository.ITimeSlotRepository;
import com.ra.service.interfaces.IChairService;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    @Autowired
    private ITheaterRepository theaterRepository ;

    @Autowired
    private IMovieRepository movieRepository ;

    @Autowired
    private ITimeSlotRepository timeSlotRepository ;

    @Autowired
    private IChairRepository chairRepository;


    @Autowired
    private ChairMapper chairMapper ;
    public RoomResponse toRoomResponse(Room room) {
        List<Chair> list = chairRepository.findAllByRoomId(room.getId());
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .numberOfSeats(room.getNumberOfSeats())
                .status(room.getStatus())
                .movieName(room.getMovie().getName())
                .theaterName(room.getTheater().getName())
                .timeSlotName(room.getTimeSlot().getName())
                .chairResponses(list.stream().map(item -> chairMapper.toChairResponse(item)).collect(Collectors.toList()))
                .build();
    }

    public Room toEntity(RoomRequest roomRequest) throws CustomException {
        Movie movie = movieRepository.findById(roomRequest.getMovieId()).orElseThrow(()-> new CustomException("Movie Not Found")) ;
        Theater theater = theaterRepository.findById(roomRequest.getTheaterId()).orElseThrow(()-> new CustomException("Theater Not Found"));
        TimeSlot timeSlot = timeSlotRepository.findById(roomRequest.getTimeSlotId()).orElseThrow(()-> new CustomException("TimeSlot Not Found"));

        return Room.builder()
                .name(roomRequest.getName())
                .numberOfSeats(roomRequest.getNumberOfSeats())
                .status(true)
                .movie(movie)
                .theater(theater)
                .timeSlot(timeSlot)
                .build();
    }


}
