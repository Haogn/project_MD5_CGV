package com.ra.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChangePassword {
    private String oldPassword ;
    private String newPassword;
    private String confirmPassword ;
}
