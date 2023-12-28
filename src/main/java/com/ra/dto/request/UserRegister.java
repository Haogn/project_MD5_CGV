package com.ra.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRegister {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không đúng định dạng")
    @NotBlank(message = "Email không được để rỗng")
    private String email ;
    @Length(min =2 , max = 50, message = "Tên tài khoản ít nhất 2 ký tự , nhiều nhất 50 ký tự")
    @NotBlank(message = "tên tài khoản không được để trống")
    private String userName ;
    @Length(min = 6, max = 16, message = "password ít nhất 6 ký tự, nhiều nhất 16 ký tự")
    @NotBlank(message = "passwourd không đc để trống")
    private String password ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Ngày sinh không được được để trống")
    private Date birthday;
    private List<String> roles;
    private String memberLever;
}
