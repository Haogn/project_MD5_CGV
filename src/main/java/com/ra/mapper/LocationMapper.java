package com.ra.mapper;

import com.ra.dto.request.LocationRequest;
import com.ra.dto.response.LocationResponse;
import com.ra.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationResponse toLocationResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }

    public Location toEntity(LocationRequest locationRequest) {
        return Location.builder()
                .name(locationRequest.getName())
                .build();
    }
}
