package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "theater")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Theater {
    // rạp chiếu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name ;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location ;

    @OneToMany(mappedBy = "theater")
    private Set<Room> rooms ;

}
