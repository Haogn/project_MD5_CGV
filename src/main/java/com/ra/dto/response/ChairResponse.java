package com.ra.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairResponse {
    private Long id ;
    private String name ;
    private Boolean active ;
    private String roomName;
    private String timeSlotName;
    private String theaterName;
}
