package com.ra.mapper;

import com.ra.dto.request.RoomRequest;
import com.ra.dto.response.RoomResponse;
import com.ra.entity.Movie;
import com.ra.entity.Room;
import com.ra.entity.Theater;
import com.ra.exception.CustomException;
import com.ra.repository.IMovieRepository;
import com.ra.repository.ITheaterRepository;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    @Autowired
    private ITheaterRepository theaterRepository ;

    @Autowired
    private IMovieRepository movieRepository ;
    public RoomResponse toRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .numberOfSeats(room.getNumberOfSeats())
                .status(room.getStatus())
                .movieName(room.getMovie().getName())
                .theaterName(room.getTheater().getName())
                .build();
    }

    public Room toEntity(RoomRequest roomRequest) throws CustomException {
        Movie movie = movieRepository.findById(roomRequest.getMovieId()).orElseThrow(()-> new CustomException("Movie Not Found")) ;
        Theater theater = theaterRepository.findById(roomRequest.getTheaterId()).orElseThrow(()-> new CustomException("Theater Not Found"));

        return Room.builder()
                .name(roomRequest.getName())
                .numberOfSeats(roomRequest.getNumberOfSeats())
                .status(roomRequest.getStatus())
                .movie(movie)
                .theater(theater)
                .build();
    }


}
