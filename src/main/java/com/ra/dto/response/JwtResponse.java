package com.ra.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String email;
    private String userName;
    private String token ;
    private Boolean status = true;
    private Date birthday; // Ngày sinh nhật của thành viên
    private List<String> roles;
    private String memberLever;
}
