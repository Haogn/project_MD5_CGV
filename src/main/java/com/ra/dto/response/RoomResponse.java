package com.ra.dto.response;

import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomResponse {
    private Long id ;
    private String name ;
    private Long numberOfSeats ;
    private Boolean status ;
    private String  theaterName ;
    private String  movieName ;
    private String timeSlotName ;
    private List<ChairResponse> chairResponses ;
}
