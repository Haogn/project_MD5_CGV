package com.ra.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;


import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movie")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ; // tên fim
    private String director; // đạo diễn
    private String performer; // diễn viên
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date startTime,  endTime ;
    private String title ; // tiêu đề
    private String  image ; // hình ảnh đại diện
    private Double price ;
    private MovieStatus movieStatus ;
    @ManyToMany
    @JoinTable(
            name = "movie_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    private List<Categories> categories ;

}
