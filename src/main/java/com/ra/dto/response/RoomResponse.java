package com.ra.dto.response;

import lombok.*;

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
}
