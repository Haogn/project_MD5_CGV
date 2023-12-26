package com.ra.service.impl;

import com.ra.dto.request.ChairRequest;
import com.ra.dto.response.ChairResponse;
import com.ra.entity.Chair;
import com.ra.entity.Room;
import com.ra.exception.CustomException;
import com.ra.mapper.ChairMapper;
import com.ra.repository.IChairRepository;
import com.ra.repository.IRoomRepository;
import com.ra.service.interfaces.IChairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChairService implements IChairService {
    @Autowired
    private IChairRepository chairRepository ;
    @Autowired
    private IRoomRepository roomRepository ;
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

    @Override
    public ChairResponse save(ChairRequest chairRequest) throws CustomException {
        if (chairRepository.existsByName(chairRequest.getName())) {
            throw new CustomException("Exits Chair Name") ;
        }
        Chair chair = chairRepository.save(chairMapper.toEntity(chairRequest));
        return chairMapper.toChairResponse(chair);
    }

    @Override
    public ChairResponse update(Long id, ChairRequest chairRequest) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Chair Not Found")) ;
        if (chair.getName().equalsIgnoreCase(chairRequest.getName())) {
            throw new CustomException("Exits ChairName");
        }
        Room room = roomRepository.findById(chairRequest.getRoomId()).orElseThrow(()-> new CustomException("Room Not Found"));
        chair.setId(id);
        chair.setName(chairRequest.getName());
        chair.setActive(chairRequest.getActive());
        chair.setRoom(room);
        return chairMapper.toChairResponse(chairRepository.save(chair));
    }

    @Override
    public ChairResponse changeStatusChair(Long id) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Chair Not Found")) ;
        chair.setActive(!chair.getActive());
        return chairMapper.toChairResponse(chairRepository.save(chair));
    }

    @Override
    public void delete(Long id) throws CustomException {
        Chair chair = chairRepository.findById(id).orElseThrow(() -> new CustomException("Chair Not Found")) ;
        if (chair != null) {
            chairRepository.deleteById(id);
        }
    }
}
