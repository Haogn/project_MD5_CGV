package com.ra.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlotResponse {
    private Long id ;
    private String name ;
    private String starTime ;
    private String endTime ;
    private String  roomName ;
}
