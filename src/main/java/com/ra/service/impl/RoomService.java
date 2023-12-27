package com.ra.service.impl;

import com.ra.dto.request.RoomRequest;
import com.ra.dto.response.RoomResponse;
import com.ra.entity.Chair;
import com.ra.entity.Movie;
import com.ra.entity.Room;
import com.ra.entity.Theater;
import com.ra.exception.CustomException;
import com.ra.mapper.RoomMapper;
import com.ra.repository.IMovieRepository;
import com.ra.repository.IRoomRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.service.interfaces.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements IRoomService {

    @Autowired
    private IRoomRepository roomRepository ;

    @Autowired
    private IMovieRepository movieRepository ;

    @Autowired
    private ITheaterRepository theaterRepository ;

    @Autowired
    private RoomMapper roomMapper ;

    @Override
    public Page<RoomResponse> findAllRoom(Pageable pageable) {
        Page<Room> rooms = roomRepository.findAll(pageable) ;
        return rooms.map(item -> roomMapper.toRoomResponse(item));
    }

    @Override
    public RoomResponse findById(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Room Not Found"));
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse save(RoomRequest roomRequest) throws CustomException {
        if (roomRepository.existsByName(roomRequest.getName())) {
            throw new CustomException("Exits RoomName") ;
        }
        Room room = roomRepository.save(roomMapper.toEntity(roomRequest)) ;

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse update(Long id, RoomRequest roomRequest) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Room Not Found"));

        Movie movie = movieRepository.findById(roomRequest.getMovieId()).orElseThrow(()-> new CustomException("Movie Not Found"));
        Theater theater = theaterRepository.findById(roomRequest.getTheaterId()).orElseThrow(() -> new CustomException("Theater Not Found"));

        room.setId(id);
        room.setName(roomRequest.getName());
        room.setNumberOfSeats(roomRequest.getNumberOfSeats());
        room.setStatus(true);
        room.setMovie(movie);
        room.setTheater(theater);
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public RoomResponse changeStatusRoom(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Room Not Found"));
        room.setStatus(!room.getStatus());
        return roomMapper.toRoomResponse(roomRepository.save(room));
    }

    @Override
    public void delete(Long id) throws CustomException {
        Room room = roomRepository.findById(id).orElseThrow(() -> new CustomException("Room Not Found"));
        if (room != null) {
            roomRepository.deleteById(id);
        }
    }
}
