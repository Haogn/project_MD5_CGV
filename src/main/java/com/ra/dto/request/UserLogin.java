package com.ra.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLogin {
    @Length(min =2 , max = 50, message = "Tên tài khoản ít nhất 2 ký tự , nhiều nhất 50 ký tự")
    @NotBlank(message = "tên tài khoản không được để trống")
    private String userName ;
    @Length(min = 6, max = 16, message = "password ít nhất 6 ký tự, nhiều nhất 16 ký tự")
    @NotBlank(message = "passwourd không đc để trống")
    private String password ;
}
