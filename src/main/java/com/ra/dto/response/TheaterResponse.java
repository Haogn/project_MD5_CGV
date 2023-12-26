package com.ra.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterResponse {
    private Long id ;
    private String name;
    private String locationName;
}
