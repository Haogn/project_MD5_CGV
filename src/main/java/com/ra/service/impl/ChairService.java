package com.ra.service.impl;

import com.ra.dto.request.ChairRequest;
import com.ra.dto.response.ChairResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.entity.Theater;
import com.ra.entity.TimeSlot;
import com.ra.exception.CustomException;
import com.ra.mapper.ChairMapper;
import com.ra.repository.IChairRepository;
import com.ra.repository.IRoomRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.repository.ITimeSlotRepository;
import com.ra.service.interfaces.IChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChairService implements IChairService {
    @Autowired
    private IChairRepository chairRepository ;
    @Autowired
    private IRoomRepository roomRepository ;
    @Autowired
    private ITimeSlotRepository timeSlotRepository;
    @Autowired
    private ITheaterRepository theaterRepository ;
    @Autowired
    private ChairMapper chairMapper ;
    @Override
    public List<ChairResponse> findAllChair() {
        List<Chair> list = chairRepository.findAll() ;
        return list.stream().map(item -> chairMapper.toChairResponse(item)).collect(Collectors.toList());
    }

    @Override
    public ChairResponse findById(Long id) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Chair Not Found")) ;
        return chairMapper.toChairResponse(chair);
    }

//    @Override
//    public List<ChairResponse> save(ChairRequest chairRequest) throws CustomException {
//
//        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
//        List<Chair> list = chairRepository.findAll();
//        for (Chair c : list) {
//            if (c.getRoom().getTheater().equals(room.getTheater()) && c.getRoom().equals(room) ){
//                throw new CustomException("Rooms already exist in Theater") ;
//            }
//            if (c.getRoom().equals(room)  && c.getName().equals(chairRequest.getName())){
//                throw new CustomException("Duplicate Chair Names in Rooms") ;
//            }
//        }
//
//        Long numberOfSeats = room.getNumberOfSeats();
//        List<Chair> chairs = new ArrayList<>();
//        for (int i = 0; i < numberOfSeats; i++) {
//            Chair chair = new Chair() ;
//            chair.setName(chairRequest.getName() +"-"+ i);
//            chair.setActive(false);
//
//            chairs.add(chair);
//        }
//        chairRepository.saveAll(chairs);
//        return chairs.stream().map(item -> chairMapper.toChairResponse(item)).collect(Collectors.toList());
//
//    }
//
//    @Override
//    public ChairResponse update(Long id, ChairRequest chairRequest) throws CustomException {
//        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Chair Not Found")) ;
//
//        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
//
//        chair.setId(id);
//        chair.setName(chairRequest.getName());
//        chair.setActive(false);
//        chair.setRoom(room);
//        return chairMapper.toChairResponse(chairRepository.save(chair));
//
//    }

    @Override
    public ChairResponse changeStatusChair(Long id) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Chair Not Found")) ;
        chair.setActive(!chair.getActive());
        return chairMapper.toChairResponse(chairRepository.save(chair));
    }


}
