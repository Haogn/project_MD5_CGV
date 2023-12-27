package com.ra.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MovieResponse {
    private Long id ;
    private String name ; // tên fim
    private String director; // đạo diễn
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date startTime,  endTime ;
    private String title ; // tiêu đề
    private String  image ; // hình ảnh đại diện
    private Double price ;
    private String movieStatus ;
    private List<String> categoryName ;
}
