package com.ra.dto.request;

import com.ra.entity.Room;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlotRequest {
    private String name ;
    private String starTime ;
    private String endTime ;
    private Long roomId ;
}
