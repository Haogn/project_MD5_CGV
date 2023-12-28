package com.ra.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomRequest {
    private String name ;
    private Long numberOfSeats ;
    private Long theaterId ;
    private Long movieId ;
    private Long timeSlotId ;
}
