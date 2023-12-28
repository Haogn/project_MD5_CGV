package com.ra.service.impl;

import com.ra.dto.request.LocationRequest;
import com.ra.dto.response.LocationResponse;
import com.ra.entity.Location;
import com.ra.exception.CustomException;
import com.ra.mapper.LocationMapper;
import com.ra.repository.ILocationRepository;
import com.ra.service.interfaces.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private ILocationRepository locationRepository ;

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public Page<LocationResponse> findAllLocation(String name, Pageable pageable) {
        Page<Location> locationPage ;
        if (name.isEmpty() || name == null) {
            locationPage = locationRepository.findAll(pageable);
        } else  {
            locationPage = locationRepository.findAllByNameContainingIgnoreCase(name, pageable);
        }
        return locationPage.map(item -> locationMapper.toLocationResponse(item));
    }

    @Override
    public LocationResponse findById(Long id) throws CustomException {
        Location location = locationRepository.findById(id).orElseThrow(()-> new CustomException("Location Not Found"));
        return locationMapper.toLocationResponse(location);
    }

    @Override
    public LocationResponse save(LocationRequest locationRequest) throws CustomException {
        if(locationRepository.existsByName(locationRequest.getName())) {
            throw new CustomException("Exits LocationName");
        }
        Location location = locationRepository.save(locationMapper.toEntity(locationRequest)) ;
        return locationMapper.toLocationResponse(location);
    }

    @Override
    public LocationResponse update(Long id, LocationRequest locationRequest) throws CustomException {
        Location location = locationRepository.findById(id).orElseThrow(()-> new CustomException("Location Not Found")) ;

        location.setId(id);
        location.setName(locationRequest.getName());
        return locationMapper.toLocationResponse(locationRepository.save(location));
    }


}
