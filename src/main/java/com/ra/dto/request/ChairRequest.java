package com.ra.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairRequest {
    private String name ;
    private Boolean active ;
    private Long roomId;
}
