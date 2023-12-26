package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "time_slot")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;
    private String starTime ;
    private String endTime ;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room ;
    @OneToOne
    @JoinColumn(name = "chair_id")
    private Chair chair;
}
