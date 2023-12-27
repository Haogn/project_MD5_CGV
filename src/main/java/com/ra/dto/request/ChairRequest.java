package com.ra.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairRequest {
    private String name ;
    private Long roomId;
    private Long timeSlotId;
    private Long theaterId;
}
