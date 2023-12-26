package com.ra.service.impl;

import com.ra.dto.request.TimeSlotRequest;
import com.ra.dto.response.TimeSlotResponse;
import com.ra.entity.Room;
import com.ra.entity.TimeSlot;
import com.ra.exception.CustomException;
import com.ra.mapper.TimeSlotMapper;
import com.ra.repository.IRoomRepository;
import com.ra.repository.ITimeSlotRepository;
import com.ra.service.interfaces.ITimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TimeSlotService implements ITimeSlotService {

    @Autowired
    private ITimeSlotRepository timeSlotRepository ;
    @Autowired
    private TimeSlotMapper timeSlotMapper ;
    @Autowired
    private IRoomRepository roomRepository;

    @Override
    public Page<TimeSlotResponse> findAllTimeSlot(Pageable pageable) {
        Page<TimeSlot> timeSlots = timeSlotRepository.findAll(pageable) ;
        return timeSlots.map(item -> timeSlotMapper.toTimeSlotMapper(item));
    }

    @Override
    public TimeSlotResponse findById(Long id) throws CustomException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(()-> new CustomException("TimeSlot Not Found"));
        return timeSlotMapper.toTimeSlotMapper(timeSlot);
    }

    @Override
    public TimeSlotResponse save(TimeSlotRequest timeSlotRequest) throws CustomException {
        if (timeSlotRepository.existsByName(timeSlotRequest.getName())){
            throw new CustomException("Exits TimeSlot Name") ;
        }
        TimeSlot timeSlot = timeSlotRepository.save(timeSlotMapper.toEntity(timeSlotRequest));
        return timeSlotMapper.toTimeSlotMapper(timeSlot);
    }

    @Override
    public TimeSlotResponse update(Long id, TimeSlotRequest timeSlotRequest) throws CustomException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(()-> new CustomException("TimeSlot Not Found"));
        if (timeSlotRequest.getName().equalsIgnoreCase(timeSlot.getName())) {
            throw new CustomException("Exits TimeSlot Name");
        }
        Room room = roomRepository.findById(timeSlotRequest.getRoomId()).orElseThrow(() -> new CustomException("Room Not Found"));
        timeSlot.setId(id);
        timeSlot.setName(timeSlotRequest.getName());
        timeSlot.setStarTime(timeSlotRequest.getStarTime());
        timeSlot.setEndTime(timeSlot.getEndTime());
        timeSlot.setRoom(room);
        return timeSlotMapper.toTimeSlotMapper(timeSlotRepository.save(timeSlot));
    }

    @Override
    public void delete(Long id) throws CustomException {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(()-> new CustomException("TimeSlot Not Found"));
        if (timeSlot != null) {
            timeSlotRepository.deleteById(id);
        }
    }
}
