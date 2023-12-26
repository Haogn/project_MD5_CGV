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
    private Boolean status ;
    private Long theaterId ;
    private Long movieId ;
}
