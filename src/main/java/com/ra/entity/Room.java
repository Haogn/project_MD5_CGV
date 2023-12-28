package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private Long numberOfSeats ;
    private Boolean status; // 'Trống' hoặc 'Đã đặt'
    @ManyToOne
    @JoinColumn(name = "theater_id", referencedColumnName = "id")
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie ;

    @ManyToOne
    @JoinColumn(name = "time_slot_id")
    private TimeSlot timeSlot ;


}
