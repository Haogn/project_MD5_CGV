package com.ra.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String userName;
    private Boolean status;
    private Date birthday;
    private List<String> roles;
    private String memberLever;
    private Integer scorePoints ;
}
