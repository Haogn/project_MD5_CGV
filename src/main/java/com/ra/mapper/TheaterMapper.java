package com.ra.mapper;

import com.ra.dto.request.TheaterRequest;
import com.ra.dto.response.TheaterResponse;
import com.ra.entity.Location;
import com.ra.entity.Theater;
import com.ra.exception.CustomException;
import com.ra.repository.ILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TheaterMapper {

    @Autowired
    private ILocationRepository locationRepository ;
    public TheaterResponse toTheaterResponse(Theater theater) {
        return TheaterResponse.builder()
                .id(theater.getId())
                .name(theater.getName())
                .locationName(theater.getLocation().getName())
                .build();
    }

    public Theater toEntity(TheaterRequest theaterRequest) throws CustomException {
        Location location = locationRepository.findById(theaterRequest.getLocationId()).orElseThrow(() -> new CustomException("Location Not Found"));
        return Theater.builder()
                .name(theaterRequest.getName())
                .location(location)
                .build();
    }
}
