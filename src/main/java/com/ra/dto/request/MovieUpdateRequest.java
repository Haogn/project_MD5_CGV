package com.ra.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieUpdateRequest {
    private String name ; // tên fim
    private String director; // đạo diễn
    private String performer; // diễn viên
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startTime,  endTime ;
    private String title ; // tiêu đề
    private MultipartFile image ; // hình ảnh đại diện
    private Double price ;
}
