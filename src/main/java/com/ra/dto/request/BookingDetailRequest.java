package com.ra.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingDetailRequest {

    private Long timeSlotId ;
    private Long roomId ;
    private Long theaterId;
    private Long movieId;
    private Long chaiId;
}
