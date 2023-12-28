package com.ra.service.impl;

import com.ra.dto.request.TheaterRequest;
import com.ra.dto.response.TheaterResponse;
import com.ra.entity.Location;
import com.ra.entity.Theater;
import com.ra.exception.CustomException;
import com.ra.mapper.TheaterMapper;
import com.ra.repository.ILocationRepository;
import com.ra.repository.ITheaterRepository;
import com.ra.service.interfaces.ITheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TheaterService implements ITheaterService {

    @Autowired
    private ITheaterRepository theaterRepository ;

    @Autowired
    private TheaterMapper theaterMapper ;

    @Autowired
    private ILocationRepository locationRepository ;

    @Override
    public Page<TheaterResponse> findAllTheater(String name, Pageable pageable) {
        Page<Theater> theaterPage ;
        if (name == null || name.isEmpty()) {
            theaterPage = theaterRepository.findAll(pageable) ;
        } else {
            theaterPage = theaterRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return theaterPage.map(item -> theaterMapper.toTheaterResponse(item));
    }

    @Override
    public TheaterResponse findById(Long id) throws CustomException {
        Theater theater = theaterRepository.findById(id).orElseThrow(()-> new CustomException("Theater Not Found"));
        return theaterMapper.toTheaterResponse(theater);
    }

    @Override
    public TheaterResponse save(TheaterRequest theaterRequest) throws CustomException {
        if (theaterRepository.existsByName(theaterRequest.getName())){
            throw  new CustomException("Exits TheaterName") ;
        }
        Theater theater = theaterRepository.save(theaterMapper.toEntity(theaterRequest));
        return theaterMapper.toTheaterResponse(theater);
    }

    @Override
    public TheaterResponse update(Long id, TheaterRequest theaterRequest) throws CustomException {
        Theater theater = theaterRepository.findById(id).orElseThrow(() -> new CustomException("Theater Not Found"));

        Location location = locationRepository.findById(theaterRequest.getLocationId()).orElseThrow(()-> new CustomException("Location Not Found"));
        theater.setId(id);
        theater.setName(theaterRequest.getName());
        theater.setLocation(location);
        return theaterMapper.toTheaterResponse(theaterRepository.save(theater));
    }


}
